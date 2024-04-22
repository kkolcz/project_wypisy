package wypisy.example.wypisy.PDF;

import ch.qos.logback.core.joran.conditional.IfAction;
import com.lowagie.text.*;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import jdk.jfr.Category;
import wypisy.example.wypisy.model.*;

import java.awt.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Pdf {


    public void ProductList(Wypis wypis , Document document){


        Font fontTitle= FontFactory.getFont((FontFactory.HELVETICA));
        Font font8 = FontFactory.getFont(FontFactory.HELVETICA, 8);
        Font font10 = FontFactory.getFont(FontFactory.HELVETICA, 10);
        fontTitle.setSize(18);


        float width = document.getPageSize().getWidth();
        float height = document.getPageSize().getHeight();

        float[] columnDefinitionSize = {6,50.33F, 30.33F, 13.33F};
        float[] columnDefinitionSizeDetails = {10F,43.33F};
        PdfPTable table ;
        PdfPTable tableDetails ;

        PdfPCell cell=new PdfPCell();
        float pos = height / 2;

        table = new PdfPTable(columnDefinitionSize);
        table.setHorizontalAlignment(0);
        table.setTotalWidth(width - 70);






        table.setLockedWidth(true);


        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(new Phrase("Nr.", font8));
        table.addCell(new Phrase("Product", font8));
        table.addCell(new Phrase("Opis", font8));
        table.addCell(new Phrase("Ilosc", font8));










        for (int j = 0; j <wypis.getWypisLines().size() ; j++) {

         WypisLine l=wypis.getWypisLines().get(j);

            tableDetails = new PdfPTable(columnDefinitionSizeDetails);
            tableDetails.getDefaultCell().setBackgroundColor(Color.LIGHT_GRAY);
            tableDetails.addCell(new Phrase("Nazwa:", font8));

            tableDetails.getDefaultCell().setBackgroundColor(Color.WHITE);
            tableDetails.addCell(new Phrase(l.getProduct().getName(), font8));

            tableDetails.getDefaultCell().setBackgroundColor(Color.LIGHT_GRAY);
            tableDetails.addCell(new Phrase("Nr.M3:", font8));

            tableDetails.getDefaultCell().setBackgroundColor(Color.WHITE);
            tableDetails.addCell(new Phrase(l.getProduct().getNrM3(), font8));

            tableDetails.getDefaultCell().setBackgroundColor(Color.LIGHT_GRAY);
            tableDetails.addCell(new Phrase("Nazwa M3:", font8));

            tableDetails.getDefaultCell().setBackgroundColor(Color.WHITE);
            tableDetails.addCell(new Phrase(l.getProduct().getNameM3(), font8));


            table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
            table.addCell(new Phrase(Integer.toString(j+1), font8));

            table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
            table.getDefaultCell().setVerticalAlignment(Element.ALIGN_TOP);


            table.addCell(tableDetails);
            table.addCell(new Phrase(l.getProduct().getDescription(), font8));

            table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
            table.addCell(new Phrase(l.getUnit().toString(), font8));







        }






        document.add(table);
        //document.newPage();




    }

        public void categoryMelement(Wypis wypis , Document document, String categoryName, List<Location> locations, Generator generator){

        HashMap<String,ArrayList> mapElemen=new HashMap<>();

        locations.forEach(loc->mapElemen.put(loc.getName(),new ArrayList()));


            for (int i = 0; i < wypis.getWypisLines().size(); i++) {
                WypisLine wypisLine=wypis.getWypisLines().get(i);
                Product product=wypis.getWypisLines().get(i).getProduct();
                if (!product.getProductLineMElements().isEmpty()){
                    for (int j = 0; j < product.getProductLineMElements().size(); j++) {
                        ProductLineMElement productLine=product.getProductLineMElements().get(j);
                        ManufacturingElement element=product.getProductLineMElements().get(j).getManufacturingElement();
                        if (!element.getProcessLines().isEmpty()){
                            for (int k = 0; k < element.getProcessLines().size(); k++) {


                                ManufacturingProcess process=element.getProcessLines().get(k).getProcess();
                                if (!process.getLocation().equals(null)){

                                    if (mapElemen.containsKey(process.getLocation().getName())){

                                        String before="";
                                        String after="";
                                        int s=k+1;
                                        if (k==0 && element.getProcessLines().size()==1){before="BRAK";after="BRAK";}
                                        else if  (k==0 && element.getProcessLines().size()>1){
                                            before="BRAK";

                                            if (s>=element.getProcessLines().size()){after="BRAK";}
                                            else {after=element.getProcessLines().get(k+1).getProcess().getLocation().getName();}
                                        }
                                         else if  (k>0 && element.getProcessLines().size()>1){
                                            before=element.getProcessLines().get(k-1).getProcess().getLocation().getName();
                                            if (s>=element.getProcessLines().size()){after="BRAK";}
                                            else {after=element.getProcessLines().get(k+1).getProcess().getLocation().getName();}
                                        }


                                        LocationElement locationElement=new LocationElement(
                                                product,
                                                element,
                                                process,
                                                wypisLine.getUnit().multiply(productLine.getUnit()),
                                                before,
                                                after

                                        );

                                        mapElemen.get(process.getLocation().getName()).add(locationElement);

                                    }

                                }
                            }

                        }

                    }

                }

            }
            System.out.println();

            if (categoryName.equals("all")){

                for(Map.Entry<String, ArrayList> entry : mapElemen.entrySet()) {
                    ArrayList<LocationElement> list=entry.getValue();


                    if(!list.isEmpty()){
                        generator.setNazwa("Lokalizacja:"+entry.getKey());
                        document.newPage();

                        Font fontTitle= FontFactory.getFont((FontFactory.HELVETICA));
                        Font font8 = FontFactory.getFont(FontFactory.HELVETICA, 8);
                        Font font10 = FontFactory.getFont(FontFactory.HELVETICA, 10);
                        fontTitle.setSize(18);


                        float width = document.getPageSize().getWidth();
                        float height = document.getPageSize().getHeight();

                        float[] columnDefinitionSize = {99.99F};
                        float[] row = {10F,80.99F};
                        float[] rowInfo = {25F,70.99F};
                        float[] rowAuthor = {58.99F,7,20F};
                        float[] rowData = {19.99F,19.99F,19.99F,19.99F,19.99F,};



                        float[] columnDefinitionSizeDetails = {10F,39.99F};
                        float[] columnDefinitionSize3 = {49.99F};

                        PdfPTable table ;
                        PdfPTable tableConteiner ;


                        PdfPTable tableId;
                        PdfPTable tableInfo ;
                        PdfPTable tablDataLable ;
                        PdfPTable tablData ;
                        PdfPTable tablAuthor ;









                        PdfPCell cell=new PdfPCell();
                        float pos = height / 2;

                        table = new PdfPTable(columnDefinitionSize);

                        PdfPTable tableMid = new PdfPTable(3);
                        table.setHorizontalAlignment(0);
                        table.setTotalWidth(width - 70);

                        table.setLockedWidth(true);



                        ;


                        for (int i = 0; i < list.size(); i++) {

                            LocationElement l=list.get(i);
                            ManufacturingElement e=list.get(i).getElement();

//                           tableInfo.addCell(new Phrase("Czas:", font8));
//                           tableInfo.addCell(new Phrase(l.getProcess().getTime().toString(), font8));
//                           tableInfo.addCell(new Phrase("Opis:", font8));
//                           tableInfo.addCell(new Phrase(e.getDescription(), font8));
//
//
//                            tablData=new PdfPTable(columnDefinitionSize3);
//                            tablData.addCell(new Phrase("Informacje", font8));
//                            tablData.addCell(tableInfo);
//
//

                            //id -Info
                            tableConteiner=new PdfPTable(columnDefinitionSize);

                            tableId=new PdfPTable(row);
                            tableId.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
                            tableId.addCell(new Phrase(Integer.toString(i+1), font8));
                            tableId.getDefaultCell().setBackgroundColor(Color.getHSBColor(0.00F,0.00F,0.91F));
                            tableId.addCell(new Phrase("Element", font8));

                            //Nazwa - Czas -OpisElementu

                            tableInfo=new PdfPTable(rowInfo);
//                            tableInfo.getDefaultCell().setBackgroundColor(Color.getHSBColor(0.00F,0.00F,0.91F));
                            tableInfo.addCell(new Phrase("Nazwa:", font8));
//                            tableInfo.getDefaultCell().setBackgroundColor(Color.WHITE);
                            tableInfo.addCell(new Phrase(e.getName(), font8));

//                            tableInfo.getDefaultCell().setBackgroundColor(Color.getHSBColor(0.00F,0.00F,0.91F));
                            tableInfo.addCell(new Phrase("Opis Elementu:", font8));
//                            tableInfo.getDefaultCell().setBackgroundColor(Color.WHITE);
                            tableInfo.addCell(new Phrase(e.getDescription(), font8));

                            //-Lable-DANE
                            tablDataLable=new PdfPTable(columnDefinitionSize);
                            tablDataLable.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
                            tablDataLable.getDefaultCell().setBackgroundColor(Color.getHSBColor(0.00F,0.00F,0.91F));
                            tablDataLable.addCell(new Phrase("Dane", font8));

                            //DLUGOSC-SZEROKOSC-WYSOKOSC-ILOSC


                            tablData=new PdfPTable(rowData);
                            tablData.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
                            tablData.getDefaultCell().setBackgroundColor(Color.getHSBColor(0.00F,0.00F,0.91F));

                            tablData.addCell(new Phrase("Dlugosc (L)", font8));
                            tablData.addCell(new Phrase("Szerokosc (B)", font8));
                            tablData.addCell(new Phrase("Wysokosc (H)", font8));
                            tablData.addCell(new Phrase("Czas", font8));
                            tablData.addCell(new Phrase("Ilosc", font8));
                            tablData.getDefaultCell().setBackgroundColor(Color.WHITE);

                            tablData.addCell(new Phrase(e.getLength().toString(), font8));
                            tablData.addCell(new Phrase(e.getWidth().toString(), font8));
                            tablData.addCell(new Phrase(e.getHeight().toString(), font8));
                            tablData.addCell(new Phrase(l.getProcess().getTime().toString(), font8));
                            tablData.addCell(new Phrase(l.getUnit().setScale(0, BigDecimal.ROUND_HALF_UP).toString(), font8));

                            //
                            tablAuthor=new PdfPTable(rowAuthor);
                            tablAuthor.getDefaultCell().setBorder(Rectangle.NO_BORDER);
                            tablAuthor.addCell(new Phrase("", font8));
                            tablAuthor.getDefaultCell().setBorder(Rectangle.BOX);
                            tablAuthor.getDefaultCell().setBackgroundColor(Color.getHSBColor(0.00F,0.00F,0.91F));
                            tablAuthor.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
                            tablAuthor.getDefaultCell().setVerticalAlignment(Element.ALIGN_CENTER);
                            tablAuthor.addCell(new Phrase("Podpis:", font8));
                            tablAuthor.getDefaultCell().setBackgroundColor(Color.WHITE);

                            tablAuthor.addCell(new Phrase(" ", font10));







                            tableConteiner.addCell(tableId);
                            tableConteiner.addCell(tableInfo);
                            tableConteiner.addCell(tablDataLable);
                            tableConteiner.addCell(tablData);
                            tableConteiner.addCell(tablAuthor);

                            table.getDefaultCell().setPaddingBottom(5);
                            table.getDefaultCell().setPaddingTop(5);
                            table.getDefaultCell().setBorder(Rectangle.NO_BORDER);
                            table.addCell(tableConteiner);













                        }









                        document.add(table);

                    }










                }









            }






















        }














}
