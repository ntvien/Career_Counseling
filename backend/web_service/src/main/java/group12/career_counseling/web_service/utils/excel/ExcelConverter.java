package group12.career_counseling.web_service.utils.excel;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.ArrayList;
import java.util.List;

import static org.apache.poi.ss.usermodel.CellType.NUMERIC;
import static org.apache.poi.ss.usermodel.CellType.STRING;

public class ExcelConverter {
    public static String convertCellToString(Cell currentCell, Sheet sheet) {
        String value;
        if (currentCell.getCellType() == STRING && StringUtils.isNotEmpty(currentCell.getStringCellValue())) {
            value = currentCell.getStringCellValue();
            return value.trim();
        }
        if (currentCell.getCellType() == NUMERIC) {
            DataFormatter formatter = new DataFormatter();
            return formatter.formatCellValue(sheet.getRow(currentCell.getRowIndex()).getCell(currentCell.getColumnIndex()));
        }
        return null;
    }

    public static List<Integer> covertStringToArray(String array) {
        if (array != null) {
            String[] numbers = array.split(",");
            List<Integer> result = new ArrayList<>();
            for (String number : numbers) {
                result.add(Integer.parseInt(number));
            }
            return result;
        }
        return new ArrayList<>();
    }
}
