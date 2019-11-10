package com.group.common.core.file;

import com.alibaba.fastjson.JSON;
import com.group.common.core.exception.ServiceException;
import org.apache.commons.fileupload.DefaultFileItemFactory;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 版权：小月科技
 * 作者：dailing
 * 生成日期：2018/9/11 下午3:01
 * 描述：Excel 文件导入
 */
public class  ExcelImport {

    private Workbook wb;

    public ExcelImport(HttpServletRequest request) throws Exception{
        FileItemFactory fileItemFactory = new DefaultFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(fileItemFactory);
        List<FileItem> items = upload.parseRequest(request);

        FileItem fileItem = items.get(0);
        String fileName = fileItem.getName();
        if(StringUtils.endsWith(fileName, "xls")){
            wb = new HSSFWorkbook(fileItem.getInputStream());
        }else if(StringUtils.endsWith(fileName, "xlsx")){
            wb = new XSSFWorkbook(fileItem.getInputStream());
        }else {
            throw new ServiceException("2000","文件类型错误");
        }

        fileItem.delete();
    }

    public ExcelImport(MultipartFile files) throws Exception{

        String originalFilename = files.getOriginalFilename();
        String fileName = originalFilename.substring(originalFilename.lastIndexOf("."));
        if(StringUtils.endsWith(fileName, "xls")){
            wb = new HSSFWorkbook(files.getInputStream());
        }else if(StringUtils.endsWith(fileName, "xlsx")){
            wb = new XSSFWorkbook(files.getInputStream());
        }else {
            throw new ServiceException("2000","文件类型错误");
        }
    }

    /**
     * 将Excel数据转换成需要的集合
     * @param firstRowIndex     数据从第几行开始，默认从1开始数
     * @param fileds    每一列定义的key值
     * @param tClass    非必传，需要转换成为的对象
     * @return
     * @throws IOException
     */
    public List convertListData(int firstRowIndex, String[] fileds, Class tClass) throws IOException {
        //开始解析
        Sheet sheet = wb.getSheetAt(0);     //读取sheet 0

        int lastRowIndex = sheet.getLastRowNum();
        System.out.println("firstRowIndex: "+firstRowIndex);
        System.out.println("lastRowIndex: "+lastRowIndex);

        final List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        for(int rIndex = firstRowIndex-1; rIndex <= lastRowIndex; rIndex++) {   //遍历行
            System.out.println("rIndex: " + rIndex);
            Row row = sheet.getRow(rIndex);
            if (row != null) {
                DecimalFormat df = new DecimalFormat("#");
                Map<String, String> map = new HashMap<String, String>();
                int firstCellIndex = row.getFirstCellNum();
                int lastCellIndex = row.getLastCellNum();
                for (int cIndex = firstCellIndex; cIndex < lastCellIndex; cIndex++) {   //遍历列
                    Cell cell = row.getCell(cIndex);
                    String cellValue = "";

                    int cellType = cell.getCellType();

                    if(Cell.CELL_TYPE_NUMERIC == cellType){
                        double cellValue1 = cell.getNumericCellValue();
                        cellValue = df.format(cellValue1);
                    }
                    if(Cell.CELL_TYPE_STRING == cellType){
                        cellValue = cell.getStringCellValue();
                    }

                    map.put(fileds[cIndex], cellValue);
                }

                list.add(map);
            }
        }

        if(tClass!=null){
            List list1 = JSON.parseArray(JSON.toJSONString(list), tClass.getClasses());
            return list1;
        }else {
            return list;
        }
    }

}
