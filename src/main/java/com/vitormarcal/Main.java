package com.vitormarcal;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;

public class Main {

	private final static String DEBITO = "DébitoBancoHoras660";
	private final static String CREDITO = "CréditoBancoHoras650";
	
	public static void main(String[] args) {
		try {
			File arquivo = new File("./src/main/resources/banco_horas.xls");
			
			CalculadoraBancoDeHoras calculadoraBancoDeHoras = new CalculadoraBancoDeHoras();
			if(arquivo.exists()){
				
				FileInputStream fis = new FileInputStream(arquivo);
				HSSFWorkbook workbook= new HSSFWorkbook(fis);
				HSSFSheet sheetHoras = workbook.getSheetAt(0);
				
				Iterator<Row> rowIterator = sheetHoras.iterator();
				while (rowIterator.hasNext()) {
					Row row = rowIterator.next();
					
					Iterator<Cell> cellIterator = row.cellIterator();
					
					while (cellIterator.hasNext()){
						Cell cell = cellIterator.next();
							if(cell.getCellTypeEnum().equals(CellType.STRING)){
								
							
							String conteudo  = cell.getStringCellValue().replace("-", "");
							
							conteudo = conteudo.replace(" ", "");
							if(conteudo.contains(DEBITO)){
								conteudo = conteudo.replace(DEBITO, "");
								int hora = Integer.parseInt(conteudo.substring(0, 2));
								int minutos = Integer.parseInt(conteudo.substring(3, 5));
								
								calculadoraBancoDeHoras.incluirDebito(hora, minutos);
				
							} else if(conteudo.contains(CREDITO)){
								conteudo = conteudo.replace(CREDITO, "");
								int hora = Integer.parseInt(conteudo.substring(0, 2));
								int minutos = Integer.parseInt(conteudo.substring(3, 5));
								calculadoraBancoDeHoras.incluirCredito(hora, minutos);
							}
						}	
					}
				}
				workbook.close();
				fis.close();	
				
				System.out.println(calculadoraBancoDeHoras.mostraSituacaoDoBancoHoras());;
			}
			
			
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
