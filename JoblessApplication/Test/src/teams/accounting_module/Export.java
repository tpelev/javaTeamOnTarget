package teams.accounting_module;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Font;

import model.Invoice;




/**
 * class to export from html table to excel file
 * @author Save Savov, Sergei Slavov
 *
 */
public class Export {

	/* Class Fields */
	private FileOutputStream fileOut;
	private HSSFWorkbook wb = new HSSFWorkbook();
	private HSSFCellStyle headerStyle = wb.createCellStyle();
	private String fileName;

	/**
	 * Constructor
	 * @param fiString
	 */
	public Export(String fiString) {
		
		HSSFFont headerFont = wb.createFont();
		headerFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
		setFileName(fiString);
		headerStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		headerStyle.setFillForegroundColor(HSSFColor.PALE_BLUE.index);
		headerStyle.setFillBackgroundColor(HSSFColor.WHITE.index);
		headerStyle.setFont(headerFont);

		try {
			fileOut = new FileOutputStream("D:\\" + getFileName() + ".xls"); // set file name; Files writes to D:\
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Method to create .xls table from Dynamic HTML table
	 * and export information to it
	 * @param lstInvoices
	 * @return boolean
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @author Save Savov, Sergei Slavov
	 */
	public boolean generateSimpleExcelReport(List<Invoice> lstInvoices) {
		try {
			HSSFSheet sheet3 = wb.createSheet("Invoice DETAILS");
			// creating first row and cells on it (put names)
			HSSFRow sessionname = sheet3.createRow(0);
			HSSFCell title = sessionname.createCell(0);
			title.setCellStyle(headerStyle);
			title.setCellValue("INVOICE DETAILS");

			HSSFRow row = sheet3.createRow(0);

			HSSFCell cell0 = row.createCell(0);
			cell0.setCellStyle(headerStyle);
			cell0.setCellValue("ID");

			HSSFCell cell1 = row.createCell(1);
			cell1.setCellStyle(headerStyle);
			cell1.setCellValue("InvoiceDate");

			HSSFCell cell2 = row.createCell(2);
			cell2.setCellStyle(headerStyle);
			cell2.setCellValue("Supplayer");

			HSSFCell cell3 = row.createCell(3);
			cell3.setCellStyle(headerStyle);
			cell3.setCellValue("Demander");

			HSSFCell cell4 = row.createCell(4);
			cell4.setCellStyle(headerStyle);
			cell4.setCellValue("Advertisement Number");

			HSSFCell cell5 = row.createCell(5);
			cell5.setCellStyle(headerStyle);
			cell5.setCellValue("Quantity");

			HSSFCell cell6 = row.createCell(6);
			cell6.setCellStyle(headerStyle);
			cell6.setCellValue("Price");

			HSSFCell cell7 = row.createCell(7);
			cell7.setCellStyle(headerStyle);
			cell7.setCellValue("Tax");

			HSSFCell cell8 = row.createCell(8);
			cell8.setCellStyle(headerStyle);
			cell8.setCellValue("Tax Amount");

			HSSFCell cell9 = row.createCell(9);
			cell9.setCellStyle(headerStyle);
			cell9.setCellValue("Discount");

			HSSFCell cell10 = row.createCell(10);
			cell10.setCellStyle(headerStyle);
			cell10.setCellValue("Total Price");

			HSSFCell cell11 = row.createCell(11);
			cell11.setCellStyle(headerStyle);
			cell11.setCellValue("Day of Purchase");

			HSSFCell cell12 = row.createCell(12);
			cell12.setCellStyle(headerStyle);
			cell12.setCellValue("Deadline");

			HSSFCell cell13 = row.createCell(13);
			cell13.setCellStyle(headerStyle);
			cell13.setCellValue("Cash");

			HSSFCell cell14 = row.createCell(14);
			cell14.setCellStyle(headerStyle);
			cell14.setCellValue("Paid");

			HSSFCell cell15 = row.createCell(15);
			cell15.setCellStyle(headerStyle);
			cell15.setCellValue("Due Payment");

			// add to next rowls and cols information
			if (!lstInvoices.isEmpty()) {
				// start row
				int rowNumber = 1;

				for (Invoice s : lstInvoices) {
					HSSFRow nextrow = sheet3.createRow(rowNumber++); // updating
																		// row
																		// number
					// start col
					int cellIndex = 0;
					// aading information to each cell
					nextrow.createCell(cellIndex++).setCellValue(s.getId());
					nextrow.createCell(cellIndex++).setCellValue(s.getInvoiceDate().toString());
					nextrow.createCell(cellIndex++).setCellValue(s.getOwner().getCompanyName());
					nextrow.createCell(cellIndex++).setCellValue(s.getCompanyProfile().getCompanyName());
					nextrow.createCell(cellIndex++).setCellValue(s.getAdvId());
					nextrow.createCell(cellIndex++).setCellValue(s.getQuantity());
					nextrow.createCell(cellIndex++).setCellValue(s.getPrice());
					nextrow.createCell(cellIndex++).setCellValue(s.getTax());
					nextrow.createCell(cellIndex++).setCellValue(s.getTaxAmmount());
					nextrow.createCell(cellIndex++).setCellValue(s.getDiscount());
					nextrow.createCell(cellIndex++).setCellValue(s.getTotalPrice());
					nextrow.createCell(cellIndex++).setCellValue(s.getEventDate().toString());
					nextrow.createCell(cellIndex++).setCellValue(s.getLatePayment());
					nextrow.createCell(cellIndex++).setCellValue(s.getIsCash());
					nextrow.createCell(cellIndex++).setCellValue(s.getIsPayed());
					nextrow.createCell(cellIndex++).setCellValue(s.getDuePayment());

				}
			}
			sheet3.autoSizeColumn(0);
			sheet3.autoSizeColumn(1);
			sheet3.autoSizeColumn(2);
			sheet3.autoSizeColumn(3);
			wb.write(fileOut); // create file xls
			fileOut.flush();
			fileOut.close();
			return true;
		} catch (FileNotFoundException fe) {
			fe.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				fileOut.flush();
				fileOut.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Getter for File Name
	 * @param fileName
	 * @return String
	 * @author Save Savov, Sergei Slavov
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * Setter for FileName
	 * if file name is empty set Current Date
	 * @param fileName
	 * @author Kaloyan Tsvetkov
	 */
	public void setFileName(String fileName) {
		//if file is empty file Name = current date.
		if (fileName.equals("") || fileName.isEmpty()) {
			this.fileName = getCurrentDate();
		} else {
			this.fileName = fileName; //else file name = name.
		}
	}

	/**
	 * Private Method getting PC current Date
	 * @return String
	 * @author Kaloyan Tsvetkov
	 */
	private String getCurrentDate() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); //creating and initializing SimpleDateFormatObject
		Date date = new Date(); //creating and initializing Date Object
		return dateFormat.format(date);
	}
}
