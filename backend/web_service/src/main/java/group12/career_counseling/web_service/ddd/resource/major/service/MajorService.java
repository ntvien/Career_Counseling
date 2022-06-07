package group12.career_counseling.web_service.ddd.resource.major.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import group12.career_counseling.web_service.ddd.community.like.service.LikeService;
import group12.career_counseling.web_service.ddd.resource.major.Major;
import group12.career_counseling.web_service.ddd.resource.major.repository.IMajorRepository;
import group12.career_counseling.web_service.ddd.resource.major.request.GetAllMajorRequest;
import group12.career_counseling.web_service.ddd.resource.major.request.GetMajorListRequest;
import group12.career_counseling.web_service.ddd.resource.major.response.GetMajorListResponse;
import group12.career_counseling.web_service.ddd.resource.major.response.GetMajorResponse;
import group12.career_counseling.web_service.ddd.resource.university.request.GetUniversityListRequest;
import group12.career_counseling.web_service.ddd.resource.university.response.GetUniversityListResponse;
import group12.career_counseling.web_service.ddd.resource.university.service.IUniversityService;
import group12.career_counseling.web_service.utils.MyStringUtils;
import group12.career_counseling.web_service.utils.excel.ExcelConverter;
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
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class MajorService implements IMajorService {
    @Autowired
    private IMajorRepository majorRepository;
    @Autowired
    private IUniversityService universityService;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private LikeService likeService;

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
                    Major major = Major.builder()
                            .starNumber(0)
                            .viewNumber(0)
                            .build();
                    for (Cell currentCell : cells) {
                        switch (currentCell.getColumnIndex()) {
                            case 1:
                                String name = ExcelConverter.convertCellToString(currentCell, sheet);
                                major.setName(name);
                                major.setNameUnsigned((MyStringUtils.removeUnicode(name)));
                                break;
                            case 2:

                                major.setDescription(ExcelConverter.convertCellToString(currentCell, sheet));
                                break;
                            case 3:
                                List<Integer> numbers = ExcelConverter.covertStringToArray(ExcelConverter.convertCellToString(currentCell, sheet));
                                List<ObjectId> universities = universityService.getIdsFromNumbers(numbers);
                                major.setUniversities(universities);
                                break;
                            case 4:
                                major.setImage(ExcelConverter.convertCellToString(currentCell, sheet));
                                break;

                        }
                    }
                    majorRepository.insert(major);
                }
            }
        }
        excelFile.close();
    }

    @Override
    public GetMajorListResponse getMajorList(GetMajorListRequest getMajorListRequest) throws Exception {
        Document query = new Document();
        Document sort = new Document();
        if (StringUtils.isNotBlank(getMajorListRequest.getKeyword())) {
            query.append("$regex", new Document("nameUnsigned", getMajorListRequest.getKeyword()));
        }
        List<String> sortFields = List.of("starNumber", "viewNumber");
        if (StringUtils.isNotBlank(getMajorListRequest.getSortBy())) {
            if (sortFields.contains(getMajorListRequest.getSortBy())) {
                sort.append(getMajorListRequest.getSortBy(), -1);
            }
        }
        sort.append("nameUnsigned", 1);
        List<Major> result = majorRepository.getManyByPage(query, sort, getMajorListRequest.getPage(),
                getMajorListRequest.getSize()).orElse(new ArrayList<>());

        long total = majorRepository.count(query).orElse(0L);

        List<GetMajorListResponse.Item> response = result.stream().map(item ->
                modelMapper.map(item, GetMajorListResponse.Item.class)).collect(Collectors.toList());
        return new GetMajorListResponse(response, getMajorListRequest.getPage(), getMajorListRequest.getSize(), total);
    }

    @Override
    public List<GetMajorListResponse.Item> getAllMajor(GetAllMajorRequest getAllMajorRequest) throws Exception {
        Document query = new Document();
        if (getAllMajorRequest.getIds() != null) {
            query.append("_id", new Document("$in", getAllMajorRequest.getIds().stream().map(ObjectId::new).collect(Collectors.toList())));
        }
        List<Major> result = majorRepository.getAll(query, new Document()).orElse(new ArrayList<>());
        List<GetMajorListResponse.Item> response = result.stream().map(item ->
                        modelMapper.map(item, GetMajorListResponse.Item.class))
                .sorted(Comparator.comparing((item) -> getAllMajorRequest.getIds().indexOf(item.get_id()))).
                collect(Collectors.toList());
        return response;
    }

    @Override
    public GetMajorResponse getMajorById(String _id) throws Exception {
        Optional<Major> major = majorRepository.getById(_id);
        if (major.isEmpty()) {
            throw new HttpException(StatusCodes.NOT_FOUND);
        }
        GetMajorResponse response = modelMapper.map(major.get(), GetMajorResponse.class);
        List<GetUniversityListResponse.Item> universities = universityService.getUniversityList(major.get().getUniversities())
                .stream().map(item -> modelMapper.map(item, GetUniversityListResponse.Item.class)).collect(Collectors.toList());
        response.setUniversities(universities);
        return response;

    }

    @Override
    public void increaseView(String _id) {
        this.majorRepository.updateView(_id, 1);
    }

    @Override
    public void updateLike() {
        List<Major> majorList = majorRepository.getAll(new Document(), new Document()).orElse(new ArrayList<>());
        for (Major major : majorList) {
            long total = likeService.countLike(major.get_id().toHexString());
            Major updateMajor = Major.builder().starNumber((int) total).build();
            majorRepository.update(major.get_id().toHexString(), updateMajor);
        }
    }

    @Override
    public void handleDataset(String fileName) throws Exception {
        FileInputStream excelFile = new FileInputStream(fileName);
        Workbook workbook = new XSSFWorkbook(excelFile);
        Sheet sheet = workbook.getSheet("data");
        for (Row cells : sheet) {
            if (cells.getCell(0) == null) {
                break;
            }
            // ignore row 1 ( index 0) in file excel, only consider row 2 (index 1)
            if (cells.getRowNum() > 0) {
                int lastColumn = cells.getLastCellNum() - 1;
                System.out.println(lastColumn);
                String name = ExcelConverter.convertCellToString(cells.getCell(lastColumn), sheet);
                Optional<Major> major = majorRepository.getByQuery(new Document("name", name));
                if (major.isPresent()) {
                    Cell cell = cells.createCell(lastColumn);
                    cell.setCellValue(major.get().get_id().toHexString());
                }

            }

        }
        try (OutputStream os = new FileOutputStream("../../AI/dataset.xlsx")) {
            workbook.write(os);
        }
        excelFile.close();
    }
}

