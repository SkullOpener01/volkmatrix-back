//package com.volkmatrix.common.utils;
//
//
//import com.fonabit.common.CommonConstant;
//import com.opencsv.CSVReader;
//import com.volkmatrix.common.exception.ApiServiceException;
//import net.lingala.zip4j.ZipFile;
//import net.lingala.zip4j.model.ZipParameters;
//import net.lingala.zip4j.model.enums.CompressionLevel;
//import net.lingala.zip4j.model.enums.EncryptionMethod;
//import org.apache.commons.collections4.IteratorUtils;
//import org.apache.commons.io.FilenameUtils;
//import org.apache.poi.xssf.usermodel.XSSFSheet;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.core.env.Environment;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.*;
//import java.nio.file.Paths;
//import java.util.List;
//import java.util.zip.ZipEntry;
//import java.util.zip.ZipOutputStream;
//
//public class WapFileUtils {
//
//  private static final Logger LOGGER = LoggerFactory.getLogger(WapFileUtils.class);
//
//  public static String tranferFileToParentDir(MultipartFile uploadFile, Environment env, String dir, String name) {
//    try {
//      String ext = FilenameUtils.getExtension(uploadFile.getOriginalFilename());
//      String url = dir + name + "." + ext;
//      File file = new File(env.getProperty("DCMNT_UPLOAD_PATH") + dir + name + "." + ext);
//      if (file.exists()) {
//        file.delete();
//      }
//      uploadFile.transferTo(file);
//      file.setExecutable(true, false);
//      file.setReadable(true, false);
//      file.setWritable(true, false);
//      return url;
//    } catch (Exception e) {
//      LOGGER.error(e.getMessage());
//      throw new ApiServiceException(e.getMessage());
//    }
//
//  }
//
//
//  public static int getCampFileSize(MultipartFile file) throws IOException {
//    int totalNum = 0;
//    if (file != null) {
//      String type = file.getContentType();
//      if (type.contains(".")) {
//        String starr[] = file.getContentType().split("\\.");
//        type = starr[1];
//      }
//
//      if (CommonConstant.CSV_FORMAT.equalsIgnoreCase(type) || CommonConstant.MAC_CSV_FORMAT.equalsIgnoreCase(type) || CommonConstant.EXCEL_FORMAT.equalsIgnoreCase(type)) {
//        if (CommonConstant.CSV_FORMAT.equalsIgnoreCase(type) || CommonConstant.MAC_CSV_FORMAT.equalsIgnoreCase(type)) {
//          System.out.println();
//          CSVReader reader = new CSVReader(new InputStreamReader(file.getInputStream()));
//          totalNum = IteratorUtils.toList(reader.iterator()).size() - 1;
//          LOGGER.info("No of Records Uploaded::" + totalNum);
//
//          reader.close();
//        } else {
//          XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
//          XSSFSheet sheet = workbook.getSheetAt(0);
//          totalNum = sheet.getLastRowNum();
//        }
//      }
//    }
//    return totalNum;
//  }
//
//  public static String zipFiles(List<String> fileCsv, String uploadDir, String rprtId) {
////      public static void zipFiles(List<String> fileCsv) {
//    String zipFileName = null;
//    try {
//
//      List<String> srcFiles = fileCsv;
//
//      zipFileName = uploadDir + rprtId + ".zip";
////          final FileOutputStream fos = new FileOutputStream(Paths.get(uploadDir+srcFiles.get(0)).getParent().toAbsolutePath() + "/compressed.zip");
////              final FileOutputStream fos = new FileOutputStream(Paths.get(uploadDir+fileCsv.get(0)).getParent().toAbsolutePath() + "/"+rprtId+".zip");
//      final FileOutputStream fos = new FileOutputStream(Paths.get(uploadDir + rprtId + ".zip").getParent().toAbsolutePath() + "/" + rprtId + ".zip");
//      ZipOutputStream zipOut = new ZipOutputStream(fos);
//
//      for (String srcFile : fileCsv) {
//
//        File fileToZip = new File(uploadDir + srcFile);
//        FileInputStream fis = new FileInputStream(fileToZip);
//        ZipEntry zipEntry = new ZipEntry(fileToZip.getName());
//        zipOut.putNextEntry(zipEntry);
//
//        byte[] bytes = new byte[1024];
//        int length;
//        while ((length = fis.read(bytes)) >= 0) {
//          zipOut.write(bytes, 0, length);
//        }
//        fis.close();
//      }
//
//      zipOut.close();
//      fos.close();
//
//
////              for (String srcFile : fileCsv) {
////                  File fileToZip = new File(uploadDir+srcFile);
////                  if(fileToZip.delete()){
////                      LOGGER.info("File deleted :::"+srcFile);
////                  }
////              }
//      LOGGER.info("chekzipfilename::" + zipFileName);
//    } catch (FileNotFoundException ex) {
//      System.err.println("A file does not exist: " + ex);
//    } catch (IOException ex) {
//      System.err.println("I/O error zipFiles: " + ex);
//    }
//
//    return zipFileName;
//  }
//
//
//  public static String zipFiles(List<String> fileCsv, String uploadDir, String rprtId, String pinPass) {
////    public static String zipFilesPassProtected(List<String> fileCsv,String uploadDir,String rprtId,String pinPass) {
//    String zipFileName = null;
//    try {
////            String password="123456";
//      zipFileName = uploadDir + rprtId + ".zip";
//
////            ZipFile zipFile = new ZipFile(new File(uploadDir + "/" + rprtId + ".zip"), password.toCharArray());
////            ZipFile zipFile = new ZipFile(new File("C:\\Users\\DevUser\\Desktop\\logcheck\\" + rprtId + ".zip"), password.toCharArray());
//
//      ZipParameters zipParameters = new ZipParameters();
//      zipParameters.setEncryptFiles(true);
//      zipParameters.setCompressionLevel(CompressionLevel.NORMAL); // No compression
//      zipParameters.setEncryptionMethod(EncryptionMethod.AES);
//      ZipFile zipFile = new ZipFile(new File(uploadDir + "/" + rprtId + ".zip"), pinPass.toCharArray());
//
//      for (String srcFile : fileCsv) {
//        File fileToZip = new File(uploadDir + srcFile);
//        zipFile.addFile(fileToZip, zipParameters);
//      }
//
//      // Delete source files
////            for (String srcFile : fileCsv) {
////                File fileToZip = new File(uploadDir + srcFile);
////                if (fileToZip.delete()) {
////                    System.out.println("File deleted: " + srcFile);
////                }
////            }
//      LOGGER.info("chekzipfilename::" + zipFileName);
//
//    } catch (IOException ex) {
//      System.err.println("I/O error zipFiles: " + ex);
//    }
//    return zipFileName;
//  }
//
//}
