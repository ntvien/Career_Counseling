package group12.career_counseling.web_service.ddd.resource.university.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import group12.career_counseling.web_service.ddd.resource.major.Major;
import group12.career_counseling.web_service.ddd.resource.major.response.GetMajorListResponse;
import group12.career_counseling.web_service.ddd.resource.university.University;
import group12.career_counseling.web_service.ddd.resource.university.repository.IUniversityRepository;
import group12.career_counseling.web_service.ddd.resource.university.request.GetUniversityListRequest;
import group12.career_counseling.web_service.ddd.resource.university.request.GetUniversityNameListRequest;
import group12.career_counseling.web_service.ddd.resource.university.response.GetUniversityListResponse;
import group12.career_counseling.web_service.ddd.resource.university.response.GetUniversityNameListResponse;
import group12.career_counseling.web_service.ddd.user.counselor.Counselor;
import group12.career_counseling.web_service.ddd.user.counselor.service.ICounselorService;
import group12.career_counseling.web_service.utils.MyStringUtils;
import group12.career_counseling.web_service.utils.enumeration.mongodb.ArrayQueryOperators;
import group12.career_counseling.web_service.utils.enumeration.resource.UniversityNameField;
import group12.career_counseling.web_service.utils.excel.ExcelConverter;
import group12.career_counseling.web_service.vertx.rest.exception.ExceptionMessages;
import group12.career_counseling.web_service.vertx.rest.exception.StatusCodes;
import io.vertx.ext.web.handler.HttpException;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UniversityService implements IUniversityService {
    @Autowired
    private IUniversityRepository universityRepository;
    @Autowired
    private ICounselorService counselorService;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public University getUniversityById(String id) {
        return this.universityRepository
                .getById(id)
                .orElseThrow(() -> new HttpException(StatusCodes.NOT_FOUND));
    }

    @Override
    public University getUniversityById(ObjectId _id) {
        return this.universityRepository
                .getById(_id)
                .orElseThrow(() -> new HttpException(StatusCodes.NOT_FOUND));
    }

    @Override
    public University GetUniversityByCounselorId(String id) {
        Counselor counselor = counselorService.getCounselorById(id);
        return this.getUniversityById(counselor.getUniversityId().toString());

    }

    @Override
    public University CreateUniversity(University university) {
        if (StringUtils.isAnyBlank(university.getName(),
                university.getContact().getAddress(),
                university.getContact().getPhone(),
                university.getDescription())) {
            throw new HttpException(StatusCodes.BAD_REQUEST, ExceptionMessages.REQUIRED_FIELDS_ARE_NULL);
        }
        Document query = new Document()
                .append(UniversityNameField.NAME, university.getName());
        universityRepository.getByQuery(query).ifPresent((value) -> {
            throw new HttpException(StatusCodes.BAD_REQUEST, ExceptionMessages.USERNAME_EXISTED);
        });
        return universityRepository.insert(university).orElseThrow(() ->
                new HttpException(StatusCodes.INTERNAL_SERVER_ERROR)
        );
    }

    @Override
    public List<University> getUniversities(int offset, int limt) {
        return universityRepository.getMany(
                new Document(),
                new Document()
                        .append(UniversityNameField.NAME_UNSIGNED, 1),
                offset,
                limt).orElseThrow(() -> new HttpException(StatusCodes.NOT_FOUND));
    }

    @Override
    public List<String> getListNameUniversities() {
        return universityRepository.getMany(
                new Document(),
                new Document()
                        .append(UniversityNameField.NAME_UNSIGNED, 1),
                new Document()
                        .append(UniversityNameField.NAME, 1),
                0,
                0).orElseThrow(() -> new HttpException(StatusCodes.NOT_FOUND))
                .stream()
                .map(University::getName)
                .collect(Collectors.toList());
    }

    @Override
    public void uploadFile(String fileName) throws Exception {
        FileInputStream excelFile = new FileInputStream(fileName);
        Workbook workbook = new XSSFWorkbook(excelFile);
        for (Sheet sheet : workbook) {
            for (Row cells : sheet) {
                if (cells.getCell(0) == null || cells.getCell(0).getStringCellValue().isBlank()) {
                    break;
                }
                // ignore row 1 ( index 0) in file excel, only consider row 2 (index 1)
                if (cells.getRowNum() > 0) {
                    University university = University.builder()
                            .starNumber(0)
                            .viewNumber(0)
                            .build();
                    University.Contact contact = new University.Contact();

                    for (Cell currentCell : cells) {
                        switch (currentCell.getColumnIndex()) {
                            case 1:
                                String name = ExcelConverter.convertCellToString(currentCell, sheet);
                                university.setName(name);
                                university.setNameUnsigned(MyStringUtils.removeUnicode(name));
                                break;
                            case 2:
                                university.setDescription(ExcelConverter.convertCellToString(currentCell, sheet));
                                break;
                            case 3:
                                contact.setAddress(ExcelConverter.convertCellToString(currentCell, sheet));
                                break;
                            case 4:
                                contact.setPhone(ExcelConverter.convertCellToString(currentCell, sheet));
                                break;
                            case 5:
                                contact.setWebsite(ExcelConverter.convertCellToString(currentCell, sheet));
                                break;
                            case 6:
                                contact.setEmail(ExcelConverter.convertCellToString(currentCell, sheet));
                                break;
                            case 7:
                                university.setImage(ExcelConverter.convertCellToString(currentCell, sheet));
                                break;
                            case 8:
                                university.setLogo(ExcelConverter.convertCellToString(currentCell, sheet));
                                break;
                        }
                    }
                    university.setContact(contact);
                    universityRepository.insert(university);
                }
            }
        }
        excelFile.close();
    }

    @Override
    public List<ObjectId> getIdsFromNumbers(List<Integer> numbers) {
        List<University> universities = universityRepository.getAll(new Document(), new Document()).orElse(new ArrayList<>());
        return numbers.stream().map(item -> universities.get(item - 1).get_id()).collect(Collectors.toList());
    }

    @Override
    public GetUniversityListResponse getUniversityList(GetUniversityListRequest getUniversityListRequest) throws Exception {
        Document query = new Document();
        Document sort = new Document();
        if (StringUtils.isNotBlank(getUniversityListRequest.getKeyword())) {
            query.append("$regex", new Document("nameUnsigned", getUniversityListRequest.getKeyword()));
        }
        List<String> sortFields = List.of("starNumber", "viewNumber");
        if (StringUtils.isNotBlank(getUniversityListRequest.getSortBy())) {
            if (sortFields.contains(getUniversityListRequest.getSortBy())) {
                sort.append(getUniversityListRequest.getSortBy(), -1);
            }
        }
        sort.append("nameUnsigned", 1);
        List<University> result = universityRepository.getManyByPage(query, sort, getUniversityListRequest.getPage(),
                getUniversityListRequest.getSize()).orElse(new ArrayList<>());

        long total = universityRepository.count(query).orElse(0L);
        List<GetUniversityListResponse.Item> response = result.stream().map(item ->
                modelMapper.map(item, GetUniversityListResponse.Item.class))
                .collect(Collectors.toList());
        return new GetUniversityListResponse(response, getUniversityListRequest.getPage(), getUniversityListRequest.getSize(), total);
    }

    @Override
    public List<University> getUniversityList(List<ObjectId> universities) {
        Document query = new Document()
                .append("_id", new Document("$in", universities));
        Document sort = new Document("starNumber", -1);
        return universityRepository.getAll(query, sort).orElse(new ArrayList<>());
    }

    @Override
    public GetUniversityNameListResponse getUniversityNameList(GetUniversityNameListRequest getUniversityNameListRequest) throws Exception {
        List<University> universities = universityRepository.getManyByPage(new Document(), new Document(),
                getUniversityNameListRequest.getPage(), getUniversityNameListRequest.getSize()).orElse(new ArrayList<>());
        List<GetUniversityNameListResponse.Item> result = universities.stream().map(item ->
                modelMapper.map(item, GetUniversityNameListResponse.Item.class)).collect(Collectors.toList());
        long total = universityRepository.count(new Document()).orElse(0L);
        return new GetUniversityNameListResponse(result, getUniversityNameListRequest.getPage(),
                getUniversityNameListRequest.getSize(), total);
    }

    @Override
    public void increaseView(String universityId) {
        this.universityRepository.updateView(universityId, 1);
    }


}
