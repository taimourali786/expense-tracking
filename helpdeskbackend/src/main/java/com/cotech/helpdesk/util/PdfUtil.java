package com.cotech.helpdesk.util;

import com.aspose.pdf.Document;
import com.aspose.pdf.FormType;
import com.aspose.pdf.TextBoxField;
import com.aspose.pdf.XFA;

public class PdfUtil {

    public static void readFile() {
        String inputPDF = "/Users/taimur.ali/Downloads/imm5911e.pdf";
//        String outputPDF = "/Users/taimur.ali/Downloads/ouput.pdf";

        try (Document pdfDocument = new Document(inputPDF)) {
            if (pdfDocument.getForm().getType() == FormType.Dynamic)
            {
                System.out.println("an XFA form.");
                XFA xfaForm = pdfDocument.getForm().getXFA();
//                for (String fieldName : xfaForm.getFieldNames())
//                {
////                    System.out.println(String.format("Field: %s", fieldName));
//                }
                xfaForm.set_Item("IMM_5911[0].Page1[0].partA[0].Section1[0].FamilyName[0]","Taimour Ali");
//                xfaForm["IMM_5911[0].Page1[0].partA[0].Section1[0].FamilyName[0]"] = "John Doe";
//                string outputPdf = @"C:\Users\Usama\Downloads\filled_xfa_form.pdf";
//                pdfDocument.save(outputPDF);
                pdfDocument.save();
//                Console.WriteLine("Form fields filled and saved successfully!");
            }
            else
            {
                System.out.println("not an XFA form.");
            }
//            System.out.println(pdfDocument.getPages());
        }
        var a = 2;
    }
}
