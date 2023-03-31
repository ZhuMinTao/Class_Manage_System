package cn.source.new_class_system.base.utils;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;
import org.apache.poi.ss.usermodel.Workbook;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * @author hanmw
 */
public class ExcelPoiUtils
{

    /**
     * 默认的 excel 导出
     *
     * @param list     数据列表
     * @param fileName 导出时的excel名称
     * @param response
     */
    private static void defaultExport(List<Map<String, Object>> list, String fileName, HttpServletResponse response)
            throws IOException
    {
        //把数据添加到excel表格中
        Workbook workbook = ExcelExportUtil.exportExcel(list, ExcelType.HSSF);
        downLoadExcel(fileName, response, workbook);
    }

    /**
     * excel 导出
     *
     * @param list         数据列表
     * @param pojoClass    pojo类型
     * @param fileName     导出时的excel名称
     * @param response
     * @param exportParams 导出参数（标题、sheet名称、是否创建表头，表格类型）
     */
    private static void defaultExport(List<?> list, Class<?> pojoClass, String fileName, HttpServletResponse response
            , ExportParams exportParams)
            throws IOException
    {
        //把数据添加到excel表格中
        Workbook workbook = ExcelExportUtil.exportExcel(exportParams, pojoClass, list);
        downLoadExcel(fileName, response, workbook);
    }

    /**
     * excel 导出
     *
     * @param list      数据列表
     * @param title     表格内数据标题
     * @param sheetName sheet名称
     * @param pojoClass pojo类型
     * @param fileName  导出时的excel名称
     * @param response
     */
    public static void exportExcel(List<?> list, String title, String sheetName, Class<?> pojoClass, String fileName,
                                   HttpServletResponse response)
            throws IOException
    {
        defaultExport(list, pojoClass, fileName, response, new ExportParams(title, sheetName, ExcelType.XSSF));
    }


    /**
     * excel下载
     *
     * @param fileName 下载时的文件名称
     * @param response
     * @param workbook excel数据
     */
    private static void downLoadExcel(String fileName, HttpServletResponse response, Workbook workbook)
            throws IOException
    {
        try
        {
            response.setCharacterEncoding("UTF-8");
            response.setHeader("content-Type", "application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName + ".xlsx",
                    "UTF-8"));
            workbook.write(response.getOutputStream());
        }
        catch (Exception e)
        {
            throw new IOException(e.getMessage());
        }
    }


    /**
     * excel 导入
     *
     * @param inputStream 文件输入流
     * @param titleRows   表格内数据标题行
     * @param headerRows  表头行
     * @param pojoClass   pojo类型
     * @param <T>
     * @return
     */
    public static <T> ExcelImportResult<T> importExcelMore(InputStream inputStream, Integer titleRows,
                                                           Integer headerRows,
                                                           Class<T> pojoClass) throws IOException
    {
        if (inputStream == null)
        {
            return null;
        }
        ImportParams params = new ImportParams();
        params.setTitleRows(titleRows);
        params.setHeadRows(headerRows);
//        params.setNeedVerify(true);
        try
        {
            return ExcelImportUtil.importExcelMore(inputStream, pojoClass, params);
        }
        catch (NoSuchElementException e)
        {
            throw new IOException("excel文件不能为空");
        }
        catch (Exception e)
        {
            throw new IOException(e.getMessage());
        }
    }

}

