package group12.career_counseling.web_service.ddd.career_test.holland_questions.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import group12.career_counseling.web_service.ddd.career_test.holland_codes.HollandCode;
import group12.career_counseling.web_service.ddd.career_test.holland_codes.repository.IHollandCodeRepository;
import group12.career_counseling.web_service.ddd.career_test.holland_codes.service.IHollandCodeService;
import group12.career_counseling.web_service.ddd.career_test.holland_questions.HollandQuestion;
import group12.career_counseling.web_service.ddd.career_test.holland_questions.repository.IHollandQuestionRepository;
import group12.career_counseling.web_service.ddd.resource.university.service.IUniversityService;
import group12.career_counseling.web_service.utils.MyStringUtils;
import group12.career_counseling.web_service.utils.excel.ExcelConverter;
import group12.career_counseling.web_service.vertx.rest.exception.StatusCodes;
import io.vertx.ext.web.handler.HttpException;
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
import java.util.List;
import java.util.Objects;

@Service
public class HollandQuestionService implements IHollandQuestionService {
    @Autowired
    private IHollandQuestionRepository hollandQuestionRepository;
    @Autowired
    private IUniversityService universityService;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public void uploadFile(String fileName) throws Exception {
        FileInputStream excelFile = new FileInputStream(fileName);
        Workbook workbook = new XSSFWorkbook(excelFile);
        for (Sheet sheet : workbook) {
            for (Row cells : sheet) {
                if (cells.getCell(0) == null) {
                    break;
                }
                // ignore row 1 ( index 0) in file excel, only consider row 2 (index 1)
                if (cells.getRowNum() > 0) {
                    HollandQuestion hollandQuestion = HollandQuestion.builder()
                            .build();
                    for (Cell currentCell : cells) {
                        switch (currentCell.getColumnIndex()) {
                            case 1:
                                hollandQuestion.setContent(ExcelConverter.convertCellToString(currentCell, sheet));
                                break;
                            case 2:
                                hollandQuestion.setCodeId(new ObjectId(Objects.requireNonNull(ExcelConverter.convertCellToString(currentCell, sheet))));
                                break;
                        }
                    }
                    hollandQuestionRepository.insert(hollandQuestion);
                }
            }
        }
        excelFile.close();
    }

    @Override
    public List<HollandQuestion> getHollandQuestionList(String codeId) throws Exception {
        return hollandQuestionRepository.getAll(new Document("codeId", new ObjectId(codeId)), new Document()).orElseThrow(() -> new HttpException(StatusCodes.INTERNAL_SERVER_ERROR));
    }
}

