/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.posta.crm.controller;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.html.simpleparser.HTMLWorker;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
import com.posta.crm.cambios.service.ProcesoService;
import com.posta.crm.entity.Client;
import com.posta.crm.entity.ProcessEmpresario;
import com.posta.crm.entity.SelfAssessment;
import com.posta.crm.entity.canvas.CanvasModel;
import com.posta.crm.entity.canvas.CostComponent;
import com.posta.crm.entity.empresario.ConceptosGenerales;
import com.posta.crm.enums.Answer;
import com.posta.crm.repository.ClientRepository;
import com.posta.crm.repository.SelfAssessmentRepository;
import com.posta.crm.repository.canvas.CanvasModelRepository;
import com.posta.crm.repository.empresario.ProcessEmpresarioRepository;

import jakarta.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author crowl
 */
@RestController
@RequestMapping("/pdf")
@CrossOrigin(origins = "*")
public class PDFCOntroller {

    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private ProcessEmpresarioRepository processEmpresarioRepository;
    @Autowired
    private ProcesoService processService;
    @Autowired
    private SelfAssessmentRepository respoSelf;
    @Autowired
    private CanvasModelRepository repoCanva;

    @GetMapping("/generarPdf/{id}")
    public void generarPDF(HttpServletResponse response, @PathVariable Long id) {
        // Establecer el tipo de contenido de la respuesta como PDF
        response.setContentType("application/pdf");
        // Establecer el encabezado para indicar la descarga del archivo PDF
        response.setHeader("Content-Disposition", "attachment; filename=Contacto.pdf");

        try {
            // Crear un nuevo documento PDF
            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, response.getOutputStream());

            // Abrir el documento para agregar contenido
            document.open();

//            String imagePathIzq = new File("camaraHD.jpg").getAbsolutePath();
//
//            // Agregar la imagen en la parte superior izquierda
//            Image image = Image.getInstance(imagePathIzq);
//            image.scaleToFit(100, 100); // Escala la imagen para que tenga un ancho y alto de 100 unidades
//            image.setAbsolutePosition(36, 750); // Coordenadas (36, 770) en el PDF (medida en unidades, donde 1 unidad = 1/72 pulgadas)
//            document.add(image);
//
//            String imagePathDer = new File("CodigoRCP-CreacionContacto.jpg").getAbsolutePath();
//
//            // Agregar la imagen en la parte superior izquierda
//            Image image1 = Image.getInstance(imagePathDer);
//            image1.scaleToFit(150, 150); // Escala la imagen para que tenga un ancho y alto de 100 unidades
//            image1.setAbsolutePosition(PageSize.A4.getWidth() - 180, 760); // Coordenadas (36, 770) en el PDF (medida en unidades, donde 1 unidad = 1/72 pulgadas)
//            document.add(image1);
            String imagePathIzq = new File("ImagenCamaraComercioIzquierda.jpg").getAbsolutePath();
            Image headerImageIzq = Image.getInstance(imagePathIzq);
            headerImageIzq.scaleToFit(150, headerImageIzq.getHeight());
            float imageX1 = 36;
            float imageY1 = PageSize.A4.getHeight() - 20 - headerImageIzq.getScaledHeight();
            headerImageIzq.setAbsolutePosition(imageX1, imageY1);
            document.add(headerImageIzq);

            String imagePathDer = new File("CodigoRCP-CreacionContacto.jpg").getAbsolutePath();
            Image headerImageDer = Image.getInstance(imagePathDer);
            headerImageDer.scaleToFit(100, 100);
            float imageX2 = PageSize.A4.getWidth() - 136;
            float imageY2 = PageSize.A4.getHeight() - 20 - headerImageDer.getScaledHeight();
            headerImageDer.setAbsolutePosition(imageX2, imageY2);
            document.add(headerImageDer);

            //Imagen Derecha pero a la izquierda de la imagen del metodo de abajo
            String imagePathIzq3 = new File("ImagenCDE_Empresarial.jpg").getAbsolutePath();
            Image headerImageIzq3 = Image.getInstance(imagePathIzq3);
            headerImageIzq3.scaleToFit(150, 150); // Ajusta el tamaño de la tercera imagen según tus necesidades
            float imageX3 = imageX2 - headerImageIzq3.getScaledWidth() - 10; // Coloca la tercera imagen a la izquierda de la segunda imagen
            float imageY3 = imageY2 - 10; // Mantiene la misma altura que la segunda imagen
            headerImageIzq3.setAbsolutePosition(imageX3, imageY3);
            document.add(headerImageIzq3);

            // Agregar contenido al PDF
            // Crear un párrafo vacío antes del título para agregar espacio entre el título y el primer párrafo
            // Establecer el estilo de fuente para el título
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, Font.BOLD);
            Paragraph title = new Paragraph("Datos del Cliente", titleFont);
            title.setSpacingBefore(40f);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(new Paragraph("\n\n"));

            // Aquí deberías obtener los datos de tu base de datos, si los necesitas
            Client client = clientRepository.findById(id).get();
            // Establecer el estilo de fuente para el contenido
            Font contentFont = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.NORMAL);
            Font atributos = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.NORMAL, BaseColor.DARK_GRAY);
            String tipo;
            String tipoNego;
            String tipoServ;
            String genero = client.getGender().name();

            if (genero.equals("FEMALE")) {
                genero = "Femenino";
            } else if (genero.equals("MALE")) {
                genero = "Masculino";
            } else if (genero.equals("LGBTQ")) {
                genero = "LGBTIQ+";
            }
            if (client.getType().equals("businessman")) {
                tipo = "Empresario";
            } else {
                tipo = "Emprendedor";
            }
            if (client.getType().equals("businessman")) {
                tipoNego = client.getCompanyName();
            } else {
                tipoNego = client.getBusinessIdea();
            }
            if (client.getType().equals("businessman")) {
                tipoServ = client.getCiiu().getTitulo().toString();
            } else {
                tipoServ = client.getProduct();
            }

            Date fecha = client.getRegdate();
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            String fechaFormateada = sdf.format(fecha);
            // Simulamos datos de ejemplo para agregar al PDF
//            String nombre = "John Doe";
//            int edad = 30;
//            String email = "johndoe@example.com";

            // Crear un párrafo con los datos y agregarlo al PDF
            Paragraph paragraph = new Paragraph();
            paragraph.add(new Phrase("Fecha: ", contentFont));
            paragraph.add(new Phrase(fechaFormateada, atributos));
            paragraph.add(new Phrase("              Municipio: ", contentFont));
            paragraph.add(new Phrase(client.getMunicipio().getName(), atributos));
            paragraph.add(new Phrase("\nAsesor: ", contentFont));
            paragraph.add(new Phrase(client.getUser().getName() + " " + client.getUser().getLastName(), atributos));

            paragraph.add(new Phrase("\nClasificación de Cliente: ", contentFont));
            paragraph.add(new Phrase(tipo, atributos));
            paragraph.add(new Phrase("\nNombres y Apellidos: ", contentFont));
            paragraph.add(new Phrase(client.getName() + " " + client.getLastName() + " ", atributos));
            paragraph.add(new Phrase("                          Documento/NIT: ", contentFont));
            paragraph.add(new Phrase(client.getNIT().toString(), atributos));
            paragraph.add(new Phrase("\nGénero: ", contentFont));
            paragraph.add(new Phrase(genero, atributos));
            paragraph.add(new Phrase("\nNombre de la Empresa o Idea de Negocio: ", contentFont));
            paragraph.add(new Phrase(tipoNego, atributos));
            paragraph.add(new Phrase("\nProducto o Servicio a Comercializar: ", contentFont));
            paragraph.add(new Phrase(tipoServ, atributos));
            paragraph.add(new Phrase("\nN° Teléfono: ", contentFont));
            paragraph.add(new Phrase(client.getPhone(), atributos));
            paragraph.add(new Phrase("                          Correo Electrónico: ", contentFont));
            paragraph.add(new Phrase(client.getEmail(), atributos));
            paragraph.add(new Phrase("\nDirección: ", contentFont));
            paragraph.add(new Phrase(client.getAddress(), atributos));
            paragraph.add(new Phrase("\nObservaciones: ", contentFont));
            paragraph.add(new Phrase(client.getRemarks(), atributos));

            paragraph.setSpacingAfter(10f); // Agregar espacio después del párrafo
            document.add(paragraph);

            // Cerrar el documento
            document.close();
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
            // Manejar errores aquí
        }
    }

    @GetMapping("/informe/{id}")
    public void generarInforme(HttpServletResponse response, @PathVariable Long id) {
        // Establecer el tipo de contenido de la respuesta como PDF
        response.setContentType("application/pdf");
        // Establecer el encabezado para indicar la descarga del archivo PDF
        response.setHeader("Content-Disposition", "attachment; filename=informeEmpresarial.pdf");

        try {
            // Crear un nuevo documento PDF
            //Document document = new Document(PageSize.A4);//este es el funcional
            //PdfWriter writer=PdfWriter.getInstance(document, response.getOutputStream());
            // Abrir el documento para agregar contenido
            //document.open();

            // Crear una instancia del documento y del escritor
            Document document = new Document(PageSize.A4);
            OutputStream outputStream = response.getOutputStream();
            PdfWriter writer = PdfWriter.getInstance(document, outputStream);

            // Crear una instancia de la clase PdfFooterEventHandler para manejar el pie de página
            PdfFooterEventHandler footerHandler = new PdfFooterEventHandler();
            writer.setPageEvent(footerHandler);
            // Crear una instancia de la clase PdfHeaderEventHandler para manejar el encabezado
            PdfHeaderEventHandler headerHandler = new PdfHeaderEventHandler();
            writer.setPageEvent(headerHandler);

            // Abrir el documento para escribir el contenido
            document.open();

            Client client = clientRepository.findById(id).get();
            ProcessEmpresario processEmpresario = processEmpresarioRepository.findByClient(client.getId());
            // Agregar contenido al PDF
            // Crear un párrafo vacío antes del título para agregar espacio entre el título y el primer párrafo
            //Crear Fecha para portada
            Date currentDate = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM yyyy");
            String formattedDate = dateFormat.format(currentDate).toUpperCase();

            // Establecer el estilo de fuente para el título
            document.add(new Paragraph("\n\n"));
            document.add(new Paragraph("\n\n"));
            Font titleFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 16, Font.BOLD);
            Paragraph title = new Paragraph(client.getCompanyName().toUpperCase(), titleFont);
            title.setSpacingBefore(20f);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(new Paragraph("\n\n"));
            document.add(new Paragraph("\n\n"));
            document.add(new Paragraph("\n\n"));
            // Aquí deberías obtener los datos de tu base de datos, si los necesitas
            // Establecer el estilo de fuente para el contenido
            Font contentFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 14, Font.BOLD);
            //Font atributos = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.NORMAL, BaseColor.DARK_GRAY);
            Font contentFont1 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.BOLD);
            Font contentFont2 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.NORMAL);
            Font contentFont3 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.BOLD);

            // Crear un párrafo con los datos y agregarlo al PDF
            Paragraph paragraph = new Paragraph();
            paragraph.add(new Phrase("Informe de Diagnóstico para Empresarios", contentFont));
            paragraph.add(new Phrase("\n\n\n\n\n\n" + client.getUser().getName().toUpperCase() + " " + client.getUser().getLastName().toUpperCase(), contentFont1));
            paragraph.add(new Phrase("\nASESOR/A CCV", contentFont1));
            paragraph.add(new Phrase("\n\n\n\n\n\nPROGRAMA DE FORTALECIMIENTO EMPRESARIAL", contentFont1));
            paragraph.add(new Phrase("\nCENTRO DE DESARROLLO EMPRESARIAL CDE-SBDC", contentFont1));
            paragraph.add(new Phrase("\nVILLAVICENCIO", contentFont1));
            paragraph.add(new Phrase("\n" + formattedDate, contentFont1));

            paragraph.setSpacingAfter(10f); // Agregar espacio después del párrafo
            paragraph.setAlignment(Element.ALIGN_CENTER);
            document.add(paragraph);

            //Agregar Pagina nueva
            document.newPage();
            document.add(new Paragraph("\n\n"));
            Font titleFont1 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 14, Font.BOLD);
            Paragraph title2 = new Paragraph("CONTENIDO", titleFont1);
            title2.setSpacingBefore(20f);
            title2.setAlignment(Element.ALIGN_CENTER);
            document.add(title2);
            document.add(new Paragraph("\n\n"));
            Paragraph paragraph1 = new Paragraph();
            paragraph1.add(new Phrase("PRESENTACIÓN.......................................................................................3\n"
                    + "1. INFORMACION BÁSICA DE LA EMPRESA..............................................................................5\n"
                    + "     1.1 RESEÑA HISTÓRICA........................................................................................5\n"
                    + "2. INFORME DE DIANÓSTICO.........................................................................................6\n"
                    + "     2.2. DIAGNÓSTICO.............................................................................................6\n"
                    + "     2.2.1  AREA LINEAMIENTOS BÁSICOS ESTRATÉGICOS........................................6\n"
                    + "     2.2.2 AREA DE MERCADEO Y VENTAS..................................................................6\n"
                    + "     2.2.3 AREA PRODUCCIÓN Y OPERACIONES..........................................................6\n"
                    + "     2.2.4 AREA FINANCIERA.........................................................................................6\n"
                    + "     2.2.5 AREA TALENTO HUMANO...............................................................................6\n"
                    + "     2.2.6 NECESIDADES DE FORMACION (Si Aplica)..........................................................6\n"
                    + "     2.2.7 AREA ASOCIATIVIDAD (Si Aplica).......................................................................6\n"
                    + "     2.2.8 OTROS ASPECTOS A FORTALECER (Si Aplica).................................................7\n"
                    + "3. CONCLUSIONES Y RECOMENDACIONES.....................................................................7", contentFont2));

            paragraph1.setSpacingAfter(10f); // Agregar espacio después del párrafo
            paragraph1.setAlignment(Element.ALIGN_JUSTIFIED_ALL);
            document.add(paragraph1);

            document.newPage();
            document.add(new Paragraph("\n\n"));

            Date currentDate1 = processEmpresario.getFechaAlta();
            SimpleDateFormat dateFormat1 = new SimpleDateFormat("d 'de' MMMM 'de' yyyy", new Locale("es", "ES"));
            String formattedDate1 = dateFormat1.format(currentDate1);

            Paragraph title3 = new Paragraph("PRESENTACIÓN", titleFont1);
            title3.setSpacingBefore(20f);
            title3.setAlignment(Element.ALIGN_CENTER);
            document.add(title3);
            document.add(new Paragraph("\n\n"));
            Paragraph paragraph3 = new Paragraph();
            paragraph3.add(new Phrase("El presente “INFORME DE DIAGNÓSTICO” corresponde a la empresa " + client.getCompanyName().toUpperCase()
                    + ", a la cual se aplicó la herramienta de diagnóstico el pasado " + formattedDate1 + ".", contentFont2));
            paragraph3.add(new Phrase("\n\nLa información aquí consignada se apoya en los datos obtenidos a través de las diferentes entrevistas"
                    + " sostenidas con el empresario y de los resultados arrojados por la herramienta de diagnóstico y su respectivo análisis."
                    + " Este diagnóstico apoyará el esfuerzo de la empresa y contribuirá a la construcción de una mejora en la toma de decisiones"
                    + " estratégicas, obtener mejores logros y desarrollar las siguientes fases del “Programa de Fortalecimiento Empresarial”."
                    + "  Midiendo el impacto económico en términos de ventas, empleo, productividad, competitividad y financiación, entre otros.\n"
                    + "El presente informe permitirá definir las estratégicas y su ejecución, orientadas a combatir las debilidades y amenazas identificadas,"
                    + " e implementar las soluciones tangibles que se requieran desde los siguientes elementos y perspectivas y algunas otras variables en los que se busca el aporte a la productividad y competitividad de su empresa: ", contentFont2));
            paragraph3.setLeading(20);
            paragraph3.setSpacingAfter(10f); // Agregar espacio después del párrafo
            paragraph3.setAlignment(Element.ALIGN_JUSTIFIED);
            document.add(paragraph3);

            Paragraph tabulatedParagraph = new Paragraph();
            tabulatedParagraph.setIndentationLeft(50);
            tabulatedParagraph.add(new Phrase("1.	AREA LINEAMIENTOS BASICOS ESTRATEGICOS DE LA EMPRESA:", contentFont3));
            tabulatedParagraph.add(new Phrase(" Este elemento permite identificar"
                    + " elementos básicos de la Planeación estratégica de la empresa; tales como su misión, visión, gestión organizacional, seguimiento"
                    + " a la gestión y sus procesos internos, toma de decisiones gerenciales entre otros, estos como elementos que permiten determinar"
                    + " un punto de partida y una meta clara para asegurar la sostenibilidad de la misma.", contentFont2));
            tabulatedParagraph.add(new Phrase("\n"));
            tabulatedParagraph.setIndentationLeft(50);
            tabulatedParagraph.add(new Phrase("\n2.	AREA DE MERCADEO Y VENTAS:", contentFont3));
            tabulatedParagraph.add(new Phrase(" Conjunto de procedimientos que buscan identificar la capacidad de"
                    + " la empresa en cuanto a que vender, a quienes vender, cuando vender, como vender, clientes, realizar promoción, fidelizar,"
                    + " captar y retener clientes.  Busca dar respuesta a la pregunta ¿Cómo nos ven los clientes? Mide el conocimiento de los"
                    + " clientes, de su(s) producto(s), y el posicionamiento de la marca y regularidad de la compra, entre otros.", contentFont2));
            tabulatedParagraph.add(new Phrase("\n"));
            tabulatedParagraph.setIndentationLeft(50);
            tabulatedParagraph.add(new Phrase("\n3.	AREA PRODUCCIÓN Y OPERACIONES:", contentFont3));
            tabulatedParagraph.add(new Phrase(" Mide el grado de Planeación, programación y control de las"
                    + " actividades que componen la cadena de valor y el proceso de elaboración del bien o servicio; capacidad de producción,"
                    + " capacidad instalada.  Mide la implementación y funcionamiento de los procesos internos de la empresa de cara a la obtención"
                    + " de la satisfacción de sus grupos de interés.", contentFont2));
            tabulatedParagraph.setLeading(20);
            tabulatedParagraph.setSpacingAfter(10f); // Agregar espacio después del párrafo
            tabulatedParagraph.setAlignment(Element.ALIGN_JUSTIFIED);
            document.add(tabulatedParagraph);

            document.newPage();
            document.add(new Paragraph("\n\n"));
            Paragraph tabulatedParagraph1 = new Paragraph();

            tabulatedParagraph1.setIndentationLeft(50);
            tabulatedParagraph1.add(new Phrase("\n4.	AREA FINANCIERA:", contentFont3));
            tabulatedParagraph1.add(new Phrase(" Actividades y decisiones encaminadas a maximizar el valor del negocio."
                    + "  En términos generales responde la pregunta ¿Cómo nos vemos a los ojos de los accionistas? Mide balances generales, flujo de caja,"
                    + " costos, gastos, punto equilibrio, productividad (EBITDA) las ganancias, rendimiento económico, desarrollo de la compañía y"
                    + " rentabilidad de la misma (ROE, ROI, ROA), riesgos financieros, entre otros.", contentFont2));
            tabulatedParagraph1.add(new Phrase("\n"));
            tabulatedParagraph1.setIndentationLeft(50);
            tabulatedParagraph1.add(new Phrase("\n5.	AREA TALENTO HUMANO:", contentFont3));
            tabulatedParagraph1.add(new Phrase(" Integra los procedimientos que buscan desarrollar al talento humano,"
                    + " mejorar condiciones de trabajo, velar por su adecuado reclutamiento, selección y contratación, evaluación de desempeño orientado"
                    + " a las competencias, dentro de un marco legal.  Permite responder la pregunta ¿Podemos continuar mejorando y creando valor?"
                    + " Analiza la cultura y clima organizacional para el aprendizaje y la acción del Capital Humano.", contentFont2));
            tabulatedParagraph1.add(new Phrase("\n"));
            tabulatedParagraph1.setIndentationLeft(50);
            tabulatedParagraph1.add(new Phrase("\n6.	NECESIDADES DE FORMACION O ENTRENAMIENTO (Si Aplica):", contentFont3));
            tabulatedParagraph1.add(new Phrase(" Con el fin de fortalecer las"
                    + " competencias empresariales y gerenciales del personal o de hacer parte de algunos de los programas de capacitación"
                    + ", servicio que será prestado en articulación con las Academia y el SENA.", contentFont2));
            tabulatedParagraph1.add(new Phrase("\n"));
            tabulatedParagraph1.setIndentationLeft(50);
            tabulatedParagraph1.add(new Phrase("\n7.	AREA DE ASOCIATIVIDAD (Si Aplica):", contentFont3));
            tabulatedParagraph1.add(new Phrase(" Este enfoque mide las alianzas, convenios y/o articulaciones entre la empresa y el"
                    + " sector u otras empresas que brinden competitividad manteniendo su independencia jurídica y autonomía gerencial.", contentFont2));
            tabulatedParagraph1.add(new Phrase("\n"));
            tabulatedParagraph1.setIndentationLeft(50);
            tabulatedParagraph1.add(new Phrase("\n8.	OTROS ASPECTOS A FORTALECER (Si Aplica): ", contentFont3));
            tabulatedParagraph1.add(new Phrase(" En caso de que haya necesidades muy específicas planteadas por el empresario o por el tipo de empresa,"
                    + " se puede manejar a través de este ítem siempre y cuando el asesor asignado tenga la competencia o los medios para responder a"
                    + " dicha necesidad. Ej necesidades de formular proyectos, convocatorias de proyectos especiales de desarrollo rural y empresarial,"
                    + " revisión de componentes ambientales, tecnologías de la información entre otros.", contentFont2));
            tabulatedParagraph1.setLeading(20);
            tabulatedParagraph1.setSpacingAfter(10f); // Agregar espacio después del párrafo
            tabulatedParagraph1.setAlignment(Element.ALIGN_JUSTIFIED);
            document.add(tabulatedParagraph1);

            Date currentDate2 = client.getFechaAlta();
            SimpleDateFormat dateFormat2 = new SimpleDateFormat("d 'de' MMMM 'de' yyyy", new Locale("es", "ES"));
            String formattedDate2 = dateFormat2.format(currentDate2);
            Date currentDate3 = client.getFechaAlta();

            // Crear un formato de fecha personalizado
            SimpleDateFormat dateFormat3 = new SimpleDateFormat("dd-MM-yyyy");
            String formattedDate3 = dateFormat3.format(currentDate3);

            document.newPage();
            document.add(new Paragraph("\n\n"));
            Paragraph paragraph4 = new Paragraph();
            paragraph4.setIndentationLeft(50);
            paragraph4.add(new Phrase("\nRAZÓN SOCIAL: " + client.getCompanyName(), contentFont2));
            paragraph4.add(new Phrase("\nREPRESENTANTE LEGAL: " + client.getName() + " " + client.getLastName(), contentFont2));
//            paragraph4.add(new Phrase("\nCONTACTO EMPRESA: XXXXXXXXXXXXXXXXXXXXXXX", contentFont2));
            paragraph4.add(new Phrase("\nTIPO DE EMPRESA: " + client.getTypeOfCompany().toString().toUpperCase(), contentFont2));
            paragraph4.add(new Phrase("\nDOCUMENTO/NIT: " + client.getNIT(), contentFont2));
            paragraph4.add(new Phrase("\nFECHA DE CONSTITUCIÓN: " + formattedDate3, contentFont2));
            paragraph4.add(new Phrase("\nTELÉFONO CELULAR: " + client.getPhone(), contentFont2));
            paragraph4.add(new Phrase("\nDIRECCIÓN DE LA EMPRESA: " + client.getAddress(), contentFont2));
//            paragraph4.add(new Phrase("\nPÁGINA WEB : ", contentFont2));
            paragraph4.add(new Phrase("\nEMAIL: " + client.getEmail(), contentFont2));
            paragraph4.add(new Phrase("\nSECTOR ECONÓMICO: " + client.getCiiu().getTitulo(), contentFont2));
//            paragraph4.add(new Phrase("\nPRODUCTO O SERVICIO: XXXXXXXXXXXXXXXXXXXXXXX", contentFont2));
            paragraph4.add(new Phrase("\n\n"));
            paragraph4.add(new Phrase("1.1	RESEÑA HISTÓRICA ", contentFont3));
            paragraph4.add(new Phrase("Esta empresa fue constituida desde " + formattedDate2 + ". Opera en unas instalaciones que"
                    + " son de su propiedad y que están ubicadas en el sector " + client.getMunicipio().getCountry() + " Barrio " + client.getMunicipio().getName() + ". Desarrolla sus actividades"
                    + " con " + client.getEmployeeFullTime().toString() + " empleados de planta y " + client.getEmployeePartTime().toString() + " en misión contratados a través de Desarrollo de Actividad.", contentFont2));

            paragraph4.setLeading(20);
            paragraph4.setSpacingAfter(10f); // Agregar espacio después del párrafo
            paragraph4.setAlignment(Element.ALIGN_JUSTIFIED);
            document.add(paragraph4);

            document.newPage();
            document.add(new Paragraph("\n\n"));
            Paragraph paragraph5 = new Paragraph();

            paragraph5.add(new Phrase("2. INFORME DE DIANÓSTICO ", contentFont3));
            paragraph5.setIndentationLeft(50);
            paragraph5.add(new Phrase("\n2.2. DIAGNÓSTICO ", contentFont3));
            paragraph5.add(new Phrase("\nLa información que se describe a continuación es el resultado obtenido de la información"
                    + " registrada con participación del empresario en la herramienta de diagnóstico.\n"
                    + "Se pueden incluir gráfico(S) resumen, fotografias, tablas de datos u objetos que refuercen el análisis de las áreas diagnósticadas", contentFont2));
            paragraph5.add(new Phrase("\n2.2.1 AREA LINEAMIENTOS BÁSICOS ESTRATÉGICOS ", contentFont3));
            paragraph5.add(new Phrase("\nSe realiza el análisis del área lineamientos básicos estratégicos de acuerdo"
                    + " a los principales hallazgos identificados, y con base en estos se deberá formular el plan de acción.", contentFont2));
            paragraph5.add(new Phrase("\n2.2.2 AREA DE MERCADEO Y VENTAS", contentFont3));
            paragraph5.add(new Phrase("\nSe realiza el análisis del área de mercados y ventas de acuerdo"
                    + " a los principales hallazgos identificados, y con base en estos se deberá formular el plan de acción.", contentFont2));
            paragraph5.add(new Phrase("\n2.2.3 AREA PRODUCCIÓN Y OPERACIONES", contentFont3));
            paragraph5.add(new Phrase("\nSe realiza el análisis del área de producción y operaciones  de acuerdo"
                    + " a los principales hallazgos identificados, y con base en estos se deberá formular el plan de acción.", contentFont2));
            paragraph5.add(new Phrase("\n2.2.4 AREA FINANCIERA ", contentFont3));
            paragraph5.add(new Phrase("\nSe realiza el análisis del área financiera de acuerdo"
                    + " a los principales hallazgos identificados, y con base en estos se deberá formular el plan de acción.", contentFont2));
            paragraph5.add(new Phrase("\n2.2.5 AREA TALENTO HUMANO", contentFont3));
            paragraph5.add(new Phrase("\nSe realiza el análisis del área de talento humano  de acuerdo"
                    + " a los principales hallazgos identificados, y con base en estos se deberá formular el plan de acción.", contentFont2));
            paragraph5.add(new Phrase("\n2.2.6 NECESIDADES DE FORMACION (Si Aplica)", contentFont3));
            paragraph5.add(new Phrase("\nSe realiza el análisis de las necesidades formación o entrenamiento planteadas "
                    + "por el empresario en caso de llegar a presentarse, es importante dejar claridad que en el caso de la formación"
                    + " el servicio será prestado por otra área, si es entrenamientos se plantean las temáticas de acuerdo a las fichas establecidas.", contentFont2));
            paragraph5.add(new Phrase("\n2.2.7 AREA ASOCIATIVIDAD (Si Aplica)", contentFont3));
            paragraph5.add(new Phrase("\nSe realiza el análisis del área de mercados y ventas de acuerdo"
                    + " a los principales hallazgos identificados, y con base en estos se deberá formular el plan de acción.", contentFont2));
            paragraph5.add(new Phrase("\n2.2.8 OTROS ASPECTOS A FORTALECER (Si Aplica))", contentFont3));
            paragraph5.add(new Phrase("\nSIncluir si es necesario el análisis de otras áreas o aspectos sobre los cuales se identificó"
                    + " que es relevante realizar el fortalecimiento de acuerdo a los principales hallazgos identificados y con base en estos"
                    + " se deberá formular el plan de acción. Ej Temas de licencias ambientales, implementar sistema integrados de gestión,"
                    + " proyectos especiales entre otros..", contentFont2));
            paragraph5.setLeading(20);
            paragraph5.setSpacingAfter(10f); // Agregar espacio después del párrafo
            paragraph5.setAlignment(Element.ALIGN_JUSTIFIED);
            document.add(paragraph5);

            document.newPage();
            document.add(new Paragraph("\n\n"));
            Paragraph paragraph6 = new Paragraph();
            paragraph6.add(new Phrase("3. CONCLUSIONES Y RECOMENDACIONES", contentFont3));
            paragraph6.add(new Phrase("\nEl Asesor determina según su criterio profesional los aspectos relevantes y establece la"
                    + " apertura para formular el plan de acción en las áreas que requiera mayor intervención, ", contentFont2));
            paragraph6.add(new Phrase("(focalización de las principales necesidades de la empresa).", contentFont1));
            paragraph6.add(new Phrase("\n\n"));
            paragraph6.add(new Phrase("Cordialmente,", contentFont2));
            paragraph6.add(new Phrase("\n\n"));
            paragraph6.add(new Phrase("\n\n"));
            paragraph6.add(new Phrase(client.getUser().getName().toUpperCase(), contentFont3));
            paragraph6.add(new Phrase("\nAsesor/a", contentFont2));
            paragraph6.add(new Phrase("\nCDE-SBDC", contentFont2));
            paragraph6.add(new Phrase("\n" + client.getUser().getEmail(), contentFont2));

            paragraph6.setLeading(20);
            paragraph6.setSpacingAfter(10f); // Agregar espacio después del párrafo
            paragraph6.setAlignment(Element.ALIGN_JUSTIFIED);
            document.add(paragraph6);

            // Cerrar el documento
            document.close();
            outputStream.flush();
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
            // Manejar errores aquí
        }
    }

    @GetMapping("/diagnostico/{id}")
    public void generarDiagnostico(HttpServletResponse response, @PathVariable Long id) {

        String[] nombregestion = new String[10];
        nombregestion[0] = "GESTIÓN ESTRATÉGICA COMERCIAL Y DE MARKETING";
        nombregestion[1] = "GESTIÓN DE PRODUCTIVIDAD Y DEL TALENTO HUMANO";
        nombregestion[2] = "GESTIÓN DE LA PRODUCTIVIDAD OPERACIONAL";
        nombregestion[3] = "GESTIÓN DE CALIDAD";
        nombregestion[4] = "GESTIÓN DE LA INNOVACIÓN";
        nombregestion[5] = "GESTIÓN FINANCIERA Y CONTABLE";
        nombregestion[6] = "GESTIÓN LOGÍSTICA";
        nombregestion[7] = "GESTIÓN DE LA TRANSFORMACIÓN DIGÍTAL";
        nombregestion[8] = "GESTIÓN DE SOSTENIBILIDAD AMBIENTAL";
        nombregestion[9] = "GESTIÓN DE LA PROPIEDAD INTELECTUAL E INDUSTRIAL";

        // Establecer el tipo de contenido de la respuesta como PDF
        response.setContentType("application/pdf");
        // Establecer el encabezado para indicar la descarga del archivo PDF
        response.setHeader("Content-Disposition", "attachment; filename=diagnosticoEmpresarial.pdf");

        try {

            // Crear una instancia del documento y del escritor
            Document document = new Document(PageSize.A4);
            OutputStream outputStream = response.getOutputStream();
            PdfWriter writer = PdfWriter.getInstance(document, outputStream);
            document.setMargins(document.leftMargin(), document.rightMargin(), 100, 100);

            // Crear una instancia de la clase PdfFooterEventHandler para manejar el pie de página
            PdfFooterEventHandler footerHandler = new PdfFooterEventHandler();
            writer.setPageEvent(footerHandler);
            // Crear una instancia de la clase PdfHeaderEventHandler para manejar el encabezado
            PdfHeaderEventHandler1 headerHandler = new PdfHeaderEventHandler1();
            writer.setPageEvent(headerHandler);

            // Abrir el documento para escribir el contenido
            document.open();

            Client client = clientRepository.findById(id).get();
            ProcessEmpresario processEmpresario = processEmpresarioRepository.findByClient(client.getId());
            // Agregar contenido al PDF
            // Crear un párrafo vacío antes del título para agregar espacio entre el título y el primer párrafo
            //Crear Fecha para portada
            Date currentDate = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM yyyy");
            String formattedDate = dateFormat.format(currentDate).toUpperCase();

            Font titleFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 16, Font.BOLD);
            Paragraph title = new Paragraph(client.getCompanyName().toUpperCase(), titleFont);
            title.setSpacingBefore(20f);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(new Paragraph("\n"));
            Paragraph title1 = new Paragraph("Diagnóstico Empresarial", titleFont);

            title1.setAlignment(Element.ALIGN_CENTER);
            document.add(title1);
            document.add(new Paragraph("\n"));
            // Aquí deberías obtener los datos de tu base de datos, si los necesitas
            // Establecer el estilo de fuente para el contenido
            Font contentFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 14, Font.BOLD);
            //Font atributos = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.NORMAL, BaseColor.DARK_GRAY);
            Font contentFont1 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.BOLD);
            Font contentFont2 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.NORMAL);
            Font contentFont3 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.BOLD);
            Font contentFont4 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 11, Font.NORMAL);

            //Crea tabla con 3 columnas
            PdfPTable table = new PdfPTable(3);
            table.setWidths(new float[]{5f, 75f, 20f});
            BaseColor colorHeader = new BaseColor(204, 255, 255);
            //Agrega el Titulo a la fila
            PdfPCell cell = new PdfPCell(new Paragraph("CONSOLIDADO", contentFont4));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setColspan(2);
            cell.setBackgroundColor(colorHeader);
            table.addCell(cell);
            PdfPCell cell10 = new PdfPCell(new Paragraph("CALIFICACIÓN", contentFont4));
            cell10.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell10.setBackgroundColor(colorHeader);
            table.addCell(cell10);
            //Fila Datos
            for (int i = 0; i < 10; i++) {
                PdfPCell cell0 = new PdfPCell(new Paragraph(String.valueOf(i + 1), contentFont4));
                cell0.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell0);
                PdfPCell cell1 = new PdfPCell(new Paragraph(nombregestion[i], contentFont4));
                cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell1);
                PdfPCell cell2 = new PdfPCell(new Paragraph(new DecimalFormat("#.##").format(processEmpresario.getDiagnosticoEmpresarial().getDiagnostico().getTotales().get(i)), contentFont4));
                cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell2);

            }
            BaseColor colorFooter = new BaseColor(255, 255, 204);
            PdfPCell cell3 = new PdfPCell(new Paragraph("TOTAL", contentFont4));
            cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell3.setColspan(2);
            cell3.setBackgroundColor(colorFooter);
            table.addCell(cell3);
            PdfPCell cell4 = new PdfPCell(new Paragraph(new DecimalFormat("#.##").format(processEmpresario.getDiagnosticoEmpresarial().getDiagnostico().getTotal()), contentFont4));
            cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell4.setBackgroundColor(colorFooter);

            table.addCell(cell4);
            document.add(table);

            Paragraph title2 = new Paragraph("Análisis de Resultados Diagnóstico Empresarial", titleFont);
            title.setSpacingBefore(20f);
            title2.setAlignment(Element.ALIGN_CENTER);
            document.add(title2);
            document.add(new Paragraph("\n"));

            //Contenido de Analisis de Resultados
            Paragraph parrafo = new Paragraph("GESTIÓN ESTRATÉGICA COMERCIAL Y DE MARKETING", contentFont1);
            parrafo.setAlignment(Element.ALIGN_CENTER);
            parrafo.setSpacingBefore(20f);
            document.add(parrafo);
            if (writer.getVerticalPosition(false) < parrafo.getTotalLeading()) {
                document.newPage();
            }
            Paragraph parrafo1 = new Paragraph(processEmpresario.getDiagnosticoEmpresarial().getAnalisisResultados().getGestionEstrategica(), contentFont4);
            parrafo1.setAlignment(Element.ALIGN_JUSTIFIED);
            parrafo1.setSpacingBefore(20f);
            document.add(parrafo1);

            if (writer.getVerticalPosition(true) < parrafo1.getTotalLeading()) {
                System.out.println("ALGO");
                document.newPage();
            }

            Paragraph parrafo2 = new Paragraph("GESTIÓN DE PRODUCTIVIDAD Y DEL TALENTO HUMANO", contentFont1);
            parrafo2.setAlignment(Element.ALIGN_CENTER);
            parrafo2.setSpacingBefore(20f);
            document.add(parrafo2);
            if (writer.getVerticalPosition(false) < parrafo2.getTotalLeading()) {
                document.newPage();
            }
            Paragraph parrafo3 = new Paragraph(processEmpresario.getDiagnosticoEmpresarial().getAnalisisResultados().getGestionProductividad(), contentFont4);
            parrafo3.setAlignment(Element.ALIGN_JUSTIFIED);
            parrafo3.setSpacingBefore(20f);
            document.add(parrafo3);
            if (writer.getVerticalPosition(false) < parrafo3.getTotalLeading()) {
                document.newPage();
            }

            Paragraph parrafo4 = new Paragraph("GESTIÓN DE LA PRODUCTIVIDAD OPERACIONAL", contentFont1);
            parrafo4.setAlignment(Element.ALIGN_CENTER);
            parrafo4.setSpacingBefore(20f);
            document.add(parrafo4);
            if (writer.getVerticalPosition(false) < parrafo4.getTotalLeading()) {
                document.newPage();
            }

            Paragraph parrafo5 = new Paragraph(processEmpresario.getDiagnosticoEmpresarial().getAnalisisResultados().getGestionProductividad(), contentFont4);
            parrafo5.setAlignment(Element.ALIGN_JUSTIFIED);
            parrafo5.setSpacingBefore(20f);
            document.add(parrafo5);
            if (writer.getVerticalPosition(false) < parrafo5.getTotalLeading()) {
                document.newPage();
            }

            Paragraph parrafo6 = new Paragraph("GESTIÓN DE CALIDAD", contentFont1);
            parrafo6.setAlignment(Element.ALIGN_CENTER);
            parrafo6.setSpacingBefore(20f);
            document.add(parrafo6);
            if (writer.getVerticalPosition(false) < parrafo6.getTotalLeading()) {
                document.newPage();
            }

            Paragraph parrafo7 = new Paragraph(processEmpresario.getDiagnosticoEmpresarial().getAnalisisResultados().getGestionCalidad(), contentFont4);
            parrafo7.setAlignment(Element.ALIGN_JUSTIFIED);
            parrafo7.setSpacingBefore(20f);
            document.add(parrafo7);
            if (writer.getVerticalPosition(false) < parrafo7.getTotalLeading()) {
                document.newPage();
            }

            Paragraph parrafo8 = new Paragraph("GESTIÓN DE LA INNOVACIÓN", contentFont1);
            parrafo8.setAlignment(Element.ALIGN_CENTER);
            parrafo8.setSpacingBefore(20f);
            document.add(parrafo8);
            if (writer.getVerticalPosition(false) < parrafo8.getTotalLeading()) {
                document.newPage();
            }

            Paragraph parrafo9 = new Paragraph(processEmpresario.getDiagnosticoEmpresarial().getAnalisisResultados().getGestionInnovacion(), contentFont4);
            parrafo9.setAlignment(Element.ALIGN_JUSTIFIED);
            parrafo9.setSpacingBefore(20f);
            document.add(parrafo9);
            if (writer.getVerticalPosition(false) < parrafo9.getTotalLeading()) {
                document.newPage();
            }

            Paragraph parrafo10 = new Paragraph("GESTIÓN FINANCIERA Y CONTABLE", contentFont1);
            parrafo10.setAlignment(Element.ALIGN_CENTER);
            parrafo10.setSpacingBefore(20f);
            document.add(parrafo10);
            if (writer.getVerticalPosition(false) < parrafo10.getTotalLeading()) {
                document.newPage();
            }

            Paragraph parrafo11 = new Paragraph(processEmpresario.getDiagnosticoEmpresarial().getAnalisisResultados().getGestionFinanciera(), contentFont4);
            parrafo11.setAlignment(Element.ALIGN_JUSTIFIED);
            parrafo11.setSpacingBefore(20f);
            document.add(parrafo11);
            if (writer.getVerticalPosition(false) < parrafo11.getTotalLeading()) {
                document.newPage();
            }

            Paragraph parrafo12 = new Paragraph("GESTIÓN LOGÍSTICA", contentFont1);
            parrafo12.setAlignment(Element.ALIGN_CENTER);
            parrafo12.setSpacingBefore(20f);
            document.add(parrafo12);
            if (writer.getVerticalPosition(false) < parrafo12.getTotalLeading()) {
                document.newPage();
            }

            Paragraph parrafo13 = new Paragraph(processEmpresario.getDiagnosticoEmpresarial().getAnalisisResultados().getGestionLogistica(), contentFont4);
            parrafo13.setAlignment(Element.ALIGN_JUSTIFIED);
            parrafo13.setSpacingBefore(20f);
            document.add(parrafo13);
            if (writer.getVerticalPosition(false) < parrafo13.getTotalLeading()) {
                document.newPage();
            }

            Paragraph parrafo14 = new Paragraph("GESTIÓN DE LA TRANSFORMACIÓN DIGÍTAL", contentFont1);
            parrafo14.setAlignment(Element.ALIGN_CENTER);
            parrafo14.setSpacingBefore(20f);
            document.add(parrafo14);
            if (writer.getVerticalPosition(false) < parrafo14.getTotalLeading()) {
                document.newPage();
            }

            Paragraph parrafo15 = new Paragraph(processEmpresario.getDiagnosticoEmpresarial().getAnalisisResultados().getGestionDigital(), contentFont4);
            parrafo15.setAlignment(Element.ALIGN_JUSTIFIED);
            parrafo15.setSpacingBefore(20f);
            document.add(parrafo15);
            if (writer.getVerticalPosition(false) < parrafo15.getTotalLeading()) {
                document.newPage();
            }

            Paragraph parrafo16 = new Paragraph("GESTIÓN DE SOSTENIBILIDAD AMBIENTAL", contentFont1);
            parrafo16.setAlignment(Element.ALIGN_CENTER);
            parrafo16.setSpacingBefore(20f);
            document.add(parrafo16);
            if (writer.getVerticalPosition(false) < parrafo16.getTotalLeading()) {
                document.newPage();
            }

            Paragraph parrafo17 = new Paragraph(processEmpresario.getDiagnosticoEmpresarial().getAnalisisResultados().getGestionAmbiental(), contentFont4);
            parrafo17.setAlignment(Element.ALIGN_JUSTIFIED);
            parrafo17.setSpacingBefore(20f);
            document.add(parrafo17);
            if (writer.getVerticalPosition(false) < parrafo17.getTotalLeading()) {
                document.newPage();
            }

            Paragraph parrafo18 = new Paragraph("GESTIÓN DE LA PROPIEDAD INTELECTUAL E INDUSTRIAL", contentFont1);
            parrafo18.setAlignment(Element.ALIGN_CENTER);
            parrafo18.setSpacingBefore(20f);
            document.add(parrafo18);
            if (writer.getVerticalPosition(false) < parrafo18.getTotalLeading()) {
                document.newPage();
            }

            Paragraph parrafo19 = new Paragraph(processEmpresario.getDiagnosticoEmpresarial().getAnalisisResultados().getGestionIntelectual(), contentFont4);
            parrafo19.setAlignment(Element.ALIGN_JUSTIFIED);
            parrafo19.setSpacingBefore(20f);
            document.add(parrafo19);
            if (writer.getVerticalPosition(false) < parrafo19.getTotalLeading()) {
                document.newPage();
            }

            // Cerrar el documento
            document.close();
            outputStream.flush();
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
            // Manejar errores aquí
        }
    }

    @GetMapping("/conceptos/{id}")
    public void generarConceptos(HttpServletResponse response, @PathVariable Long id) {
        String[] preguntas = {
            "¿Cumple con los documentos y registros necesarios legalmente (comerciales, tributarios, uso del suelo, registro Invima, concepto sanitario, concepto bomberos, licencia ambiental)?",
            "¿Cumple con todos los requisitos legales vigentes relacionados con el pago de la seguridad laboral del personal (seguridad social y pensiones administración de riesgos profesionales, fondos de cesantías)?",
            "¿Lleva libros de contabilidad, actas, de reformas, y de información legal? ¿se encuentran al día y debidamente registrados y archivados?",
            "¿Tiene definidas las responsabilidades y funciones de cada puesto de trabajo o cargos que desempeñan cada uno de los integrantes de la empresa?",
            "¿La empresa cuenta con un reglamento interno de trabajo?",
            "¿Cuentan con manual de procesos?",
            "¿Se tienen establecidas claramente la misión, visión, valores corportativos y política de calidad de la empresa?",
            "¿Se tienen indicadores: financieros, comerciales, producción, calidad?",
            "¿Tiene claramente definido el producto y el cliente hacia el cual está dirigido?",
            "¿Ha analizado la competencia y el entorno en general (productos sustitutos, competidores potenciales, proveedores, clientes)?",
            "¿La empresa cuenta con una imagen corporativa?",
            "¿La empresa dispone de un portafolio de productos/ servicios suficientemente?",
            "¿La empresa cuenta con metas (comerciales - financieras), medibles y verificables en un plazo de tiempo definido, con asignación del responsable de su cumplimiento?",
            "¿La empresa tiene definidas estrategias para comercializar sus servicios?",
            "¿La empresa cuenta con instalaciones y está ubicada geográficamente permitiendo el fácil acceso a clientes y proveedores?",
            "¿Cuenta con herramientas y maquinaria necesaria para la fabricación de sus productos o prestación de servicios?",
            "¿Se cuenta con áreas organizadas para las fabricación de los productos o servicios, almacenamiento de materias primas, productos en proceso y productos terminados?",
            "¿Cuenta con la disponibilidad de mano de obra calificada para la fabricación de productos o prestación de servicios?",
            "¿La empresa conoce y aplica las normas ambientales en el desarrollo de su actividad?",
            "¿La disponibilidad de materia prima está garantizada a mediano y largo plazo?",
            "¿Posee un sistema de contabilidad y costos que ofrece información confiable y oportuna para la toma de decisiones?",
            "¿La empresa realiza presupuestos?",
            "¿Posee Cuenta de Ahorro o Cuenta Corriente?"
        };

        // Establecer el tipo de contenido de la respuesta como PDF
        response.setContentType("application/pdf");
        // Establecer el encabezado para indicar la descarga del archivo PDF
        response.setHeader("Content-Disposition", "attachment; filename=conceptosGeneralesEmpresa.pdf");

        try {
            // Crear una instancia del documento y del escritor
            Document document = new Document(PageSize.A4);
            OutputStream outputStream = response.getOutputStream();
            PdfWriter writer = PdfWriter.getInstance(document, outputStream);
            document.setMargins(0, 0, 70, 30);

            // Crear una instancia de la clase PdfFooterEventHandler para manejar el pie de página
            //PdfFooterEventHandler footerHandler = new PdfFooterEventHandler();
            //writer.setPageEvent(footerHandler);
            // Crear una instancia de la clase PdfHeaderEventHandler para manejar el encabezado
            PdfHeaderEventHandler1 headerHandler = new PdfHeaderEventHandler1();
            writer.setPageEvent(headerHandler);

            // Abrir el documento para escribir el contenido
            document.open();
            Client client = clientRepository.findById(id).get();
            ProcessEmpresario processEmpresario = processEmpresarioRepository.findByClient(client.getId());

            // Aquí deberías obtener los datos de tu base de datos, si los necesitas
            // Establecer el estilo de fuente para el contenido
            Font contentFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 14, Font.BOLD);
            //Font atributos = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.NORMAL, BaseColor.DARK_GRAY);
            Font contentFont1 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.BOLD);
            Font contentFont2 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.NORMAL);
            Font contentFont3 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.BOLD);
            Font contentFont4 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 11, Font.NORMAL);
            Font contentFont5 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 10, Font.NORMAL);
            // Agregar contenido al PDF
            // Crear un párrafo vacío antes del título para agregar espacio entre el título y el primer párrafo
            //Crear Fecha para portada
            Date currentDate = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM yyyy");
            String formattedDate = dateFormat.format(currentDate).toUpperCase();

            Font titleFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 16, Font.BOLD);
            Paragraph title = new Paragraph(client.getCompanyName().toUpperCase(), titleFont);
            title.setSpacingBefore(20f);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(new Paragraph("\n"));
            Paragraph title1 = new Paragraph("Diagnóstico Empresarial- Conceptos Generales", titleFont);

            title1.setAlignment(Element.ALIGN_CENTER);
            document.add(title1);
            document.add(new Paragraph("\n"));

            // Crear una tabla con 4 columnas
            PdfPTable table = new PdfPTable(4);

            // Configurar el ancho de las columnas
            float[] columnWidths = {4, 56, 10, 30};
            table.setWidths(columnWidths);
            BaseColor colorHeader = new BaseColor(204, 255, 255);
            // Agregar encabezados de columna
            PdfPCell cell = new PdfPCell();
            cell.setPhrase(new Phrase("N°", contentFont4));
            cell.setBackgroundColor(colorHeader);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table.addCell(cell);

            cell = new PdfPCell();
            cell.setPhrase(new Phrase("CONCEPTOS GENERALES DE LA EMPRESA", contentFont4));
            cell.setBackgroundColor(colorHeader);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table.addCell(cell);

            cell = new PdfPCell();
            cell.setPhrase(new Phrase("CUMPL.", contentFont4));
            cell.setBackgroundColor(colorHeader);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table.addCell(cell);

            cell = new PdfPCell();
            cell.setPhrase(new Phrase("OBSERVACIONES", contentFont4));
            cell.setBackgroundColor(colorHeader);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table.addCell(cell);

            List<ConceptosGenerales> conceptosGenerales = processEmpresario.getDiagnosticoEmpresarial().getDiagnostico().getConceptosGenerales();
            // Agregar filas de datos (esto es un ejemplo, deberás adaptar los datos según tus necesidades)
            for (int i = 0; i < processEmpresario.getDiagnosticoEmpresarial().getDiagnostico().getConceptosGenerales().size(); i++) {
                ConceptosGenerales concepto = conceptosGenerales.get(i);
                PdfPCell cell1 = new PdfPCell(new Phrase(Integer.toString(i + 1), contentFont4));
                PdfPCell cell2 = new PdfPCell(new Phrase(preguntas[i], contentFont5));
                PdfPCell cell3 = new PdfPCell(new Phrase(concepto.getDiagEmpr().name(), contentFont4));
                PdfPCell cell4 = new PdfPCell(new Phrase(concepto.getObservaciones(), contentFont5));

                // Establecer la alineación de todas las celdas
                cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell3.setVerticalAlignment(Element.ALIGN_MIDDLE);

                // Agregar las celdas a la tabla
                table.addCell(cell1);
                table.addCell(cell2);
                table.addCell(cell3);
                table.addCell(cell4);
            }

            // Agregar la tabla al documento
            document.add(table);

            // Cerrar el documento
            document.close();
            outputStream.flush();
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
            // Manejar errores aquí
        }
    }

    @GetMapping("/estrategica/{id}")
    public void generarEstrategica(HttpServletResponse response, @PathVariable Long id) {
        String[] elementos = {
            "Mercado Objetivo",
            "Estrategia Comercial",
            "Mezcla de Mercadeo",
            "Mecanismos",
            "Tendencias",
            "Material Mercadeo",
            "Tiempos de Entrega",
            "Prototipos",
            "Generación de Ideas",
            "Competencia",
            "Precio",
            "Estrategias por Canal",
            "Marca",
            "Imagen Corporativa",
            "Fidelización",
            "Merchandising",
            "Otros Productos",
            "Internacionalización"
        };

        String[] preguntas = {
            "¿La empresa tiene identificado el mercado objetivo?",
            "¿La empresa cuenta con metas comerciales medibles y verificables en un plazo de tiempo definido, con asignación del responsable de su cumplimiento?",
            "¿La empresa asigna recursos para el mercadeo de sus servicios? (promociones, material publicitario, otros).",
            "¿La empresa evalúa periódicamente sus mecanismos de promoción y publicidad para medir su efectividad y/o continuidad?",
            "¿La empresa Investiga tendencias del sector y de su producto o servicio a nivel Local, Nacional e Internacional?",
            "¿La empresa dispone de catálogos, portafolio o flayers con las especificaciones técnicas sus productos o servicios?",
            "¿La empresa cumple con los requisitos de tiempo de entrega a sus clientes?",
            "¿La empresa elabora pruebas piloto de cada producto, antes de ser lanzado al mercado?",
            "¿La empresa promueve la creación de nuevos productos y o servicios para su negocio entre sus colaboradores?",
            "¿La empresa Investiga periódicamente a la competencia? (Precios, productos, servicio)",
            "¿Los precios de la empresa están determinados con base en el conocimiento de sus costos, de la demanda y de la competencia?",
            "¿La empresa establece estrategias por cada canal de distribución?",
            "¿La empresa tiene registrada su marca (marcas) e implementa estrategias para su posicionamiento?.",
            "¿La empresa cuenta con imagen corporativa? (Manual de Imagen Corporativa)",
            "¿La empresa cuenta con un plan de fidelización para los clientes?",
            "¿La empresa cuenta con material de merchandising?",
            "¿La empresa ofrece a sus clientes productos complementarios a su producto estrella?",
            "¿La empresa exporta o importa bienes o servicios?"
        };

        // Establecer el tipo de contenido de la respuesta como PDF
        response.setContentType("application/pdf");
        // Establecer el encabezado para indicar la descarga del archivo PDF
        response.setHeader("Content-Disposition", "attachment; filename=gestionEstrategica.pdf");

        try {
            // Crear una instancia del documento y del escritor
            Document document = new Document(PageSize.A4);
            OutputStream outputStream = response.getOutputStream();
            PdfWriter writer = PdfWriter.getInstance(document, outputStream);
            document.setMargins(0, 0, 70, 30);

            // Crear una instancia de la clase PdfFooterEventHandler para manejar el pie de página
            //PdfFooterEventHandler footerHandler = new PdfFooterEventHandler();
            //writer.setPageEvent(footerHandler);
            // Crear una instancia de la clase PdfHeaderEventHandler para manejar el encabezado
            PdfHeaderEventHandler1 headerHandler = new PdfHeaderEventHandler1();
            writer.setPageEvent(headerHandler);

            // Abrir el documento para escribir el contenido
            document.open();
            Client client = clientRepository.findById(id).get();
            ProcessEmpresario processEmpresario = processEmpresarioRepository.findByClient(client.getId());

            // Aquí deberías obtener los datos de tu base de datos, si los necesitas
            // Establecer el estilo de fuente para el contenido
            Font contentFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 14, Font.BOLD);
            //Font atributos = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.NORMAL, BaseColor.DARK_GRAY);
            Font contentFont1 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.BOLD);
            Font contentFont2 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.NORMAL);
            Font contentFont3 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.BOLD);
            Font contentFont4 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 11, Font.NORMAL);
            Font contentFont5 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 10, Font.NORMAL);
            // Agregar contenido al PDF
            // Crear un párrafo vacío antes del título para agregar espacio entre el título y el primer párrafo
            //Crear Fecha para portada
            Date currentDate = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM yyyy");
            String formattedDate = dateFormat.format(currentDate).toUpperCase();

            Font titleFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 16, Font.BOLD);
            Paragraph title = new Paragraph(client.getCompanyName().toUpperCase(), titleFont);
            title.setSpacingBefore(20f);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(new Paragraph("\n"));
            Paragraph title1 = new Paragraph("Diagnóstico Empresarial- Gestión Estratégica y de Marketing", titleFont);

            title1.setAlignment(Element.ALIGN_CENTER);
            document.add(title1);
            document.add(new Paragraph("\n"));

            // Crear una tabla con 4 columnas
            PdfPTable table = new PdfPTable(4);

            // Configurar el ancho de las columnas
            float[] columnWidths = {4, 20, 60, 14};
            table.setWidths(columnWidths);
            BaseColor colorHeader = new BaseColor(204, 255, 255);
            // Agregar encabezados de columna
            PdfPCell cell = new PdfPCell();

            cell = new PdfPCell();
            cell.setPhrase(new Phrase("1. GESTIÓN ESTRATÉGICA COMERCIAL Y DE MARKETING", contentFont4));
            cell.setBackgroundColor(colorHeader);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setColspan(3);
            table.addCell(cell);

            cell = new PdfPCell();
            cell.setPhrase(new Phrase("Calificación", contentFont4));
            cell.setBackgroundColor(colorHeader);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table.addCell(cell);

            List<Integer> esterategica = processEmpresario.getDiagnosticoEmpresarial().getDiagnostico().getGestionEstrategica();
            List<Float> totales = processEmpresario.getDiagnosticoEmpresarial().getDiagnostico().getTotales();
            // Agregar filas de datos (esto es un ejemplo, deberás adaptar los datos según tus necesidades)
            for (int i = 0; i < processEmpresario.getDiagnosticoEmpresarial().getDiagnostico().getGestionEstrategica().size(); i++) {
                Integer concepto = esterategica.get(i);
                PdfPCell cell1 = new PdfPCell(new Phrase(Integer.toString(i + 1), contentFont4));
                PdfPCell cell2 = new PdfPCell(new Phrase(elementos[i], contentFont5));
                PdfPCell cell3 = new PdfPCell(new Phrase(preguntas[i], contentFont4));
                PdfPCell cell4 = new PdfPCell(new Phrase(new DecimalFormat("#.##").format(concepto), contentFont5));

                // Establecer la alineación de todas las celdas
                cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell4.setVerticalAlignment(Element.ALIGN_MIDDLE);

                // Agregar las celdas a la tabla
                table.addCell(cell1);
                table.addCell(cell2);
                table.addCell(cell3);
                table.addCell(cell4);
            }

            BaseColor colorHeader1 = new BaseColor(255, 255, 204);
            cell = new PdfPCell();
            cell.setPhrase(new Phrase("PUNTAJE TOTAL", contentFont4));
            cell.setBackgroundColor(colorHeader1);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setColspan(3);
            table.addCell(cell);

            cell = new PdfPCell();
            cell.setPhrase(new Phrase(new DecimalFormat("#.##").format(totales.get(0)), contentFont4));
            cell.setBackgroundColor(colorHeader1);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setColspan(3);
            table.addCell(cell);

            // Agregar la tabla al documento
            document.add(table);

            // Cerrar el documento
            document.close();
            outputStream.flush();
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
            // Manejar errores aquí
        }
    }

    @GetMapping("/productividad/{id}")
    public void generarProductividad(HttpServletResponse response, @PathVariable Long id) {
        String[] elementos = {
            "Plan Estratégico",
            "Estructura organizacional",
            "Competencias",
            "Cumplimiento Normas",
            "Planeación",
            "Manejo de Indicadores",
            "Proceso de Selección",
            "Procesos de Inducción y Capacitación",
            "Seguridad y Salud en el Trabajo",
            "Incentivos",
            "Comunicación",
            "Desempeño",
            "Organizacional"
        };

        String[] preguntas = {
            "¿La empresa cuenta con un plan estratégico (Metas corporativas, Visión, Misión, Estrategia y Objetivos)?",
            "¿La empresa cuenta con una estructura organizacional (Organigrama, Manual de funciones y responsabilidades)?",
            "¿La empresa evalúa las competencias y habilidades periódicamente de sus trabajadores?",
            "¿En la empresa se cumplen las normas Tributarias, Contables, Laborales y Comerciales?",
            "¿La empresa realiza grupos de trabajo para planear las estrategias del mes (comerciales, marketing)?",
            "¿En la empresa se manejan indicadores de Gestión y Productividad?",
            "¿La empresa cuenta con procesos de selección de nuevos empleados (pruebas psicológicas, psicotécnicas y de conocimiento)?",
            "¿La empresa realiza procesos de inducción, reinducción y capacitación a los nuevos y antiguos empleados?",
            "¿La empresa cuenta con Seguridad y Salud en el Trabajo (equipo de trabajo, manuales, procedimientos)?",
            "¿La empresa maneja incentivos y recompensas por productividad y ventas y cumplimientos de metas?",
            "¿Cuentan con canales de comunicación ágiles, asertivos y oportunos los diferentes niveles de personal de la compañía (directivos, técnicos, administrativos, otros)?",
            "¿Realizan evaluación de desempeño a todos los colaboradores de la empresa?",
            "¿La empresa hace medición del clima organizacional?"
        };

        // Establecer el tipo de contenido de la respuesta como PDF
        response.setContentType("application/pdf");
        // Establecer el encabezado para indicar la descarga del archivo PDF
        response.setHeader("Content-Disposition", "attachment; filename=gestionProductividad.pdf");

        try {
            // Crear una instancia del documento y del escritor
            Document document = new Document(PageSize.A4);
            OutputStream outputStream = response.getOutputStream();
            PdfWriter writer = PdfWriter.getInstance(document, outputStream);
            document.setMargins(0, 0, 70, 30);

            // Crear una instancia de la clase PdfFooterEventHandler para manejar el pie de página
            //PdfFooterEventHandler footerHandler = new PdfFooterEventHandler();
            //writer.setPageEvent(footerHandler);
            // Crear una instancia de la clase PdfHeaderEventHandler para manejar el encabezado
            PdfHeaderEventHandler1 headerHandler = new PdfHeaderEventHandler1();
            writer.setPageEvent(headerHandler);

            // Abrir el documento para escribir el contenido
            document.open();
            Client client = clientRepository.findById(id).get();
            ProcessEmpresario processEmpresario = processEmpresarioRepository.findByClient(client.getId());

            // Aquí deberías obtener los datos de tu base de datos, si los necesitas
            // Establecer el estilo de fuente para el contenido
            Font contentFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 14, Font.BOLD);
            //Font atributos = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.NORMAL, BaseColor.DARK_GRAY);
            Font contentFont1 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.BOLD);
            Font contentFont2 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.NORMAL);
            Font contentFont3 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.BOLD);
            Font contentFont4 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 11, Font.NORMAL);
            Font contentFont5 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 10, Font.NORMAL);
            // Agregar contenido al PDF
            // Crear un párrafo vacío antes del título para agregar espacio entre el título y el primer párrafo
            //Crear Fecha para portada
            Date currentDate = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM yyyy");
            String formattedDate = dateFormat.format(currentDate).toUpperCase();

            Font titleFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 16, Font.BOLD);
            Paragraph title = new Paragraph(client.getCompanyName().toUpperCase(), titleFont);
            title.setSpacingBefore(20f);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(new Paragraph("\n"));
            Paragraph title1 = new Paragraph("Diagnóstico Empresarial- Gestión de la Productividad y del talento humano", titleFont);

            title1.setAlignment(Element.ALIGN_CENTER);
            document.add(title1);
            document.add(new Paragraph("\n"));

            // Crear una tabla con 4 columnas
            PdfPTable table = new PdfPTable(4);

            // Configurar el ancho de las columnas
            float[] columnWidths = {4, 20, 60, 14};
            table.setWidths(columnWidths);
            BaseColor colorHeader = new BaseColor(204, 255, 255);
            // Agregar encabezados de columna
            PdfPCell cell = new PdfPCell();

            cell = new PdfPCell();
            cell.setPhrase(new Phrase("2. GESTIÓN DE LA PRODUCTIVIDAD Y DEL TALENTO HUMANO", contentFont4));
            cell.setBackgroundColor(colorHeader);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setColspan(3);
            table.addCell(cell);

            cell = new PdfPCell();
            cell.setPhrase(new Phrase("Calificación", contentFont4));
            cell.setBackgroundColor(colorHeader);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table.addCell(cell);

            List<Integer> esterategica = processEmpresario.getDiagnosticoEmpresarial().getDiagnostico().getGestionProductividad();
            List<Float> totales = processEmpresario.getDiagnosticoEmpresarial().getDiagnostico().getTotales();
            // Agregar filas de datos (esto es un ejemplo, deberás adaptar los datos según tus necesidades)
            for (int i = 0; i < processEmpresario.getDiagnosticoEmpresarial().getDiagnostico().getGestionProductividad().size(); i++) {
                Integer concepto = esterategica.get(i);
                PdfPCell cell1 = new PdfPCell(new Phrase(Integer.toString(i + 1), contentFont4));
                PdfPCell cell2 = new PdfPCell(new Phrase(elementos[i], contentFont5));
                PdfPCell cell3 = new PdfPCell(new Phrase(preguntas[i], contentFont4));
                PdfPCell cell4 = new PdfPCell(new Phrase(new DecimalFormat("#.##").format(concepto), contentFont5));

                // Establecer la alineación de todas las celdas
                cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell4.setVerticalAlignment(Element.ALIGN_MIDDLE);

                // Agregar las celdas a la tabla
                table.addCell(cell1);
                table.addCell(cell2);
                table.addCell(cell3);
                table.addCell(cell4);
            }

            BaseColor colorHeader1 = new BaseColor(255, 255, 204);
            cell = new PdfPCell();
            cell.setPhrase(new Phrase("PUNTAJE TOTAL", contentFont4));
            cell.setBackgroundColor(colorHeader1);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setColspan(3);
            table.addCell(cell);

            cell = new PdfPCell();
            cell.setPhrase(new Phrase(new DecimalFormat("#.##").format(totales.get(1)), contentFont4));
            cell.setBackgroundColor(colorHeader1);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setColspan(3);
            table.addCell(cell);

            // Agregar la tabla al documento
            document.add(table);

            // Cerrar el documento
            document.close();
            outputStream.flush();
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
            // Manejar errores aquí
        }
    }

    @GetMapping("/operacional/{id}")
    public void generarOperacional(HttpServletResponse response, @PathVariable Long id) {
        String[] elementos = {
            "Procesos",
            "Producción",
            "Estrategia Operativa",
            "Estrategia Operativa",
            "Mano de Obra y Maquinaria",
            "Empaque",
            "Instalaciones",
            "Capacidad",
            "Mantenimiento",
            "Seguridad",
            "Seguridad Industrial"
        };

        String[] preguntas = {
            "¿La empresa cuenta con manuales de procesos (existen fichas técnicas de todas las materias primas, productos terminados, maquinaria y equipo)?",
            "¿La empresa cuenta con un plan de producción (objetivos, estrategias, flujogramas o protocolos de procesos para los bienes y servicios que produce)?",
            "¿La empresa tiene identificados indicadores para los procesos productivos (indicadores de entregas a tiempo, de reprocesos)?",
            "¿La empresa cuenta con metas de operación medibles y verificables en un plazo de tiempo definido, con asignación del responsable de su cumplimiento?",
            "¿La empresa cuenta con la disponibilidad de mano de obra calificada y maquinaria para responder ante un incremento de la producción o cambios en los gustos y preferencias de los clientes?",
            "¿El empaque del producto cumple con estándares y la normatividad actual?",
            "¿Cuentan con infraestructura, instalaciones y equipos para atender sus necesidades de funcionamiento y operación actual y futura?",
            "¿La empresa tiene definida su capacidad de producción o de prestación de servicios y con base en esto se coordina la utilización de los recursos físicos, humanos y financieros?",
            "¿En la empresa existe un programa de mantenimiento definido de la maquinaria y equipo de mantenimiento?",
            "¿En la empresa, la planta, los procesos, los equipos y las instalaciones en general están diseñados para procurar un ambiente seguro para el trabajador?",
            "¿La empresa posee un SSST, reglamento interno de trabajo, de higiene y una política de seguridad industrial?"
        };

        // Establecer el tipo de contenido de la respuesta como PDF
        response.setContentType("application/pdf");
        // Establecer el encabezado para indicar la descarga del archivo PDF
        response.setHeader("Content-Disposition", "attachment; filename=gestionOperacional.pdf");

        try {
            // Crear una instancia del documento y del escritor
            Document document = new Document(PageSize.A4);
            OutputStream outputStream = response.getOutputStream();
            PdfWriter writer = PdfWriter.getInstance(document, outputStream);
            document.setMargins(0, 0, 70, 30);

            // Crear una instancia de la clase PdfFooterEventHandler para manejar el pie de página
            //PdfFooterEventHandler footerHandler = new PdfFooterEventHandler();
            //writer.setPageEvent(footerHandler);
            // Crear una instancia de la clase PdfHeaderEventHandler para manejar el encabezado
            PdfHeaderEventHandler1 headerHandler = new PdfHeaderEventHandler1();
            writer.setPageEvent(headerHandler);

            // Abrir el documento para escribir el contenido
            document.open();
            Client client = clientRepository.findById(id).get();
            ProcessEmpresario processEmpresario = processEmpresarioRepository.findByClient(client.getId());

            // Aquí deberías obtener los datos de tu base de datos, si los necesitas
            // Establecer el estilo de fuente para el contenido
            Font contentFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 14, Font.BOLD);
            //Font atributos = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.NORMAL, BaseColor.DARK_GRAY);
            Font contentFont1 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.BOLD);
            Font contentFont2 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.NORMAL);
            Font contentFont3 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.BOLD);
            Font contentFont4 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 11, Font.NORMAL);
            Font contentFont5 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 10, Font.NORMAL);
            // Agregar contenido al PDF
            // Crear un párrafo vacío antes del título para agregar espacio entre el título y el primer párrafo
            //Crear Fecha para portada
            Date currentDate = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM yyyy");
            String formattedDate = dateFormat.format(currentDate).toUpperCase();

            Font titleFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 16, Font.BOLD);
            Paragraph title = new Paragraph(client.getCompanyName().toUpperCase(), titleFont);
            title.setSpacingBefore(20f);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(new Paragraph("\n"));
            Paragraph title1 = new Paragraph("Diagnóstico Empresarial- Gestión de la Productividad Operacional", titleFont);

            title1.setAlignment(Element.ALIGN_CENTER);
            document.add(title1);
            document.add(new Paragraph("\n"));

            // Crear una tabla con 4 columnas
            PdfPTable table = new PdfPTable(4);

            // Configurar el ancho de las columnas
            float[] columnWidths = {4, 20, 60, 14};
            table.setWidths(columnWidths);
            BaseColor colorHeader = new BaseColor(204, 255, 255);
            // Agregar encabezados de columna
            PdfPCell cell = new PdfPCell();

            cell = new PdfPCell();
            cell.setPhrase(new Phrase("3. GESTIÓN DE LA PRODUCTIVIDAD OPERACIONAL", contentFont4));
            cell.setBackgroundColor(colorHeader);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setColspan(3);
            table.addCell(cell);

            cell = new PdfPCell();
            cell.setPhrase(new Phrase("Calificación", contentFont4));
            cell.setBackgroundColor(colorHeader);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table.addCell(cell);

            List<Integer> esterategica = processEmpresario.getDiagnosticoEmpresarial().getDiagnostico().getGestionOperacional();
            List<Float> totales = processEmpresario.getDiagnosticoEmpresarial().getDiagnostico().getTotales();
            // Agregar filas de datos (esto es un ejemplo, deberás adaptar los datos según tus necesidades)
            for (int i = 0; i < processEmpresario.getDiagnosticoEmpresarial().getDiagnostico().getGestionOperacional().size(); i++) {
                Integer concepto = esterategica.get(i);
                PdfPCell cell1 = new PdfPCell(new Phrase(Integer.toString(i + 1), contentFont4));
                PdfPCell cell2 = new PdfPCell(new Phrase(elementos[i], contentFont5));
                PdfPCell cell3 = new PdfPCell(new Phrase(preguntas[i], contentFont4));
                PdfPCell cell4 = new PdfPCell(new Phrase(new DecimalFormat("#.##").format(concepto), contentFont5));

                // Establecer la alineación de todas las celdas
                cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell4.setVerticalAlignment(Element.ALIGN_MIDDLE);

                // Agregar las celdas a la tabla
                table.addCell(cell1);
                table.addCell(cell2);
                table.addCell(cell3);
                table.addCell(cell4);
            }

            BaseColor colorHeader1 = new BaseColor(255, 255, 204);
            cell = new PdfPCell();
            cell.setPhrase(new Phrase("PUNTAJE TOTAL", contentFont4));
            cell.setBackgroundColor(colorHeader1);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setColspan(3);
            table.addCell(cell);

            cell = new PdfPCell();
            cell.setPhrase(new Phrase(new DecimalFormat("#.##").format(totales.get(2)), contentFont4));
            cell.setBackgroundColor(colorHeader1);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setColspan(3);
            table.addCell(cell);

            // Agregar la tabla al documento
            document.add(table);

            // Cerrar el documento
            document.close();
            outputStream.flush();
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
            // Manejar errores aquí
        }
    }

    @GetMapping("/calidad/{id}")
    public void generarCalidad(HttpServletResponse response, @PathVariable Long id) {
        String[] elementos = {
            "Implementación",
            "Automatización",
            "Implementación",
            "Documental",
            "Mapeo",
            "Optimización",
            "Mejora",
            "Medición",
            "Satisfacción Cliente",
            "CRM"
        };

        String[] preguntas = {
            "¿La empresa cuenta con un sistema de calidad definido?",
            "¿La empresa tiene automatizados los procesos?",
            "¿La empresa cuenta con implementación de BPM (gestión de procesos de negocios) o similares?",
            "¿La empresa tiene documentados los procesos?",
            "¿La empresa tiene mapeo de los procesos?",
            "¿La empresa cuenta con optimización de procesos?",
            "¿La empresa cuenta con un esquema para ejecutar acciones de mejoramiento (correctivas y preventivas, pruebas metrológicas e inspecciones) necesarias para garantizar la calidad del producto o servicio?",
            "¿La empresa hace medición de la productividad?",
            "¿La empresa mide con frecuencia la satisfacción de sus clientes para diseñar estrategias de mantenimiento y fidelización?",
            "¿La empresa cuenta con herramientas para hacerle seguimiento a sus clientes? (gestión de relaciones con el cliente)"
        };

        // Establecer el tipo de contenido de la respuesta como PDF
        response.setContentType("application/pdf");
        // Establecer el encabezado para indicar la descarga del archivo PDF
        response.setHeader("Content-Disposition", "attachment; filename=gestionCalidadl.pdf");

        try {
            // Crear una instancia del documento y del escritor
            Document document = new Document(PageSize.A4);
            OutputStream outputStream = response.getOutputStream();
            PdfWriter writer = PdfWriter.getInstance(document, outputStream);
            document.setMargins(0, 0, 70, 30);

            // Crear una instancia de la clase PdfFooterEventHandler para manejar el pie de página
            //PdfFooterEventHandler footerHandler = new PdfFooterEventHandler();
            //writer.setPageEvent(footerHandler);
            // Crear una instancia de la clase PdfHeaderEventHandler para manejar el encabezado
            PdfHeaderEventHandler1 headerHandler = new PdfHeaderEventHandler1();
            writer.setPageEvent(headerHandler);

            // Abrir el documento para escribir el contenido
            document.open();
            Client client = clientRepository.findById(id).get();
            ProcessEmpresario processEmpresario = processEmpresarioRepository.findByClient(client.getId());

            // Aquí deberías obtener los datos de tu base de datos, si los necesitas
            // Establecer el estilo de fuente para el contenido
            Font contentFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 14, Font.BOLD);
            //Font atributos = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.NORMAL, BaseColor.DARK_GRAY);
            Font contentFont1 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.BOLD);
            Font contentFont2 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.NORMAL);
            Font contentFont3 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.BOLD);
            Font contentFont4 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 11, Font.NORMAL);
            Font contentFont5 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 10, Font.NORMAL);
            // Agregar contenido al PDF
            // Crear un párrafo vacío antes del título para agregar espacio entre el título y el primer párrafo
            //Crear Fecha para portada
            Date currentDate = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM yyyy");
            String formattedDate = dateFormat.format(currentDate).toUpperCase();

            Font titleFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 16, Font.BOLD);
            Paragraph title = new Paragraph(client.getCompanyName().toUpperCase(), titleFont);
            title.setSpacingBefore(20f);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(new Paragraph("\n"));
            Paragraph title1 = new Paragraph("Diagnóstico Empresarial- Gestión de Calidad", titleFont);

            title1.setAlignment(Element.ALIGN_CENTER);
            document.add(title1);
            document.add(new Paragraph("\n"));

            // Crear una tabla con 4 columnas
            PdfPTable table = new PdfPTable(4);

            // Configurar el ancho de las columnas
            float[] columnWidths = {4, 20, 60, 14};
            table.setWidths(columnWidths);
            BaseColor colorHeader = new BaseColor(204, 255, 255);
            // Agregar encabezados de columna
            PdfPCell cell = new PdfPCell();

            cell = new PdfPCell();
            cell.setPhrase(new Phrase("4. GESTIÓN DE CALIDAD", contentFont4));
            cell.setBackgroundColor(colorHeader);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setColspan(3);
            table.addCell(cell);

            cell = new PdfPCell();
            cell.setPhrase(new Phrase("Calificación", contentFont4));
            cell.setBackgroundColor(colorHeader);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table.addCell(cell);

            List<Integer> esterategica = processEmpresario.getDiagnosticoEmpresarial().getDiagnostico().getGestionCalidad();
            List<Float> totales = processEmpresario.getDiagnosticoEmpresarial().getDiagnostico().getTotales();
            // Agregar filas de datos (esto es un ejemplo, deberás adaptar los datos según tus necesidades)
            for (int i = 0; i < processEmpresario.getDiagnosticoEmpresarial().getDiagnostico().getGestionCalidad().size(); i++) {
                Integer concepto = esterategica.get(i);
                PdfPCell cell1 = new PdfPCell(new Phrase(Integer.toString(i + 1), contentFont4));
                PdfPCell cell2 = new PdfPCell(new Phrase(elementos[i], contentFont5));
                PdfPCell cell3 = new PdfPCell(new Phrase(preguntas[i], contentFont4));
                PdfPCell cell4 = new PdfPCell(new Phrase(new DecimalFormat("#.##").format(concepto), contentFont5));

                // Establecer la alineación de todas las celdas
                cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell4.setVerticalAlignment(Element.ALIGN_MIDDLE);

                // Agregar las celdas a la tabla
                table.addCell(cell1);
                table.addCell(cell2);
                table.addCell(cell3);
                table.addCell(cell4);
            }

            BaseColor colorHeader1 = new BaseColor(255, 255, 204);
            cell = new PdfPCell();
            cell.setPhrase(new Phrase("PUNTAJE TOTAL", contentFont4));
            cell.setBackgroundColor(colorHeader1);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setColspan(3);
            table.addCell(cell);

            cell = new PdfPCell();
            cell.setPhrase(new Phrase(new DecimalFormat("#.##").format(totales.get(3)), contentFont4));
            cell.setBackgroundColor(colorHeader1);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setColspan(3);
            table.addCell(cell);

            // Agregar la tabla al documento
            document.add(table);

            // Cerrar el documento
            document.close();
            outputStream.flush();
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
            // Manejar errores aquí
        }
    }

    @GetMapping("/innovacion/{id}")
    public void generarInnovacion(HttpServletResponse response, @PathVariable Long id) {
        String[] elementos = {
            "Desarrollo",
            "Sofisticación",
            "Desarrollo",
            "Innovación"
        };

        String[] preguntas = {
            "¿La empresa ha implementado prácticas/metodologías para la mejora del producto?",
            "¿La empresa ha implementado pruebas piloto, de laboratorio para mejorar el producto (diseño, nuevos materiales, nuevos servicios)?",
            "¿La empresa ha hecho gestión para estandarizar su producto o servicio?",
            "¿En la empresa se investiga y se obtiene información sobre nuevas tecnologías, respecto a procesos, mercados, procesos administrativos, nuevas tendencias del producto o servicio?"
        };

        // Establecer el tipo de contenido de la respuesta como PDF
        response.setContentType("application/pdf");
        // Establecer el encabezado para indicar la descarga del archivo PDF
        response.setHeader("Content-Disposition", "attachment; filename=gestionInnovacion.pdf");

        try {
            // Crear una instancia del documento y del escritor
            Document document = new Document(PageSize.A4);
            OutputStream outputStream = response.getOutputStream();
            PdfWriter writer = PdfWriter.getInstance(document, outputStream);
            document.setMargins(0, 0, 70, 30);

            // Crear una instancia de la clase PdfFooterEventHandler para manejar el pie de página
            //PdfFooterEventHandler footerHandler = new PdfFooterEventHandler();
            //writer.setPageEvent(footerHandler);
            // Crear una instancia de la clase PdfHeaderEventHandler para manejar el encabezado
            PdfHeaderEventHandler1 headerHandler = new PdfHeaderEventHandler1();
            writer.setPageEvent(headerHandler);

            // Abrir el documento para escribir el contenido
            document.open();
            Client client = clientRepository.findById(id).get();
            ProcessEmpresario processEmpresario = processEmpresarioRepository.findByClient(client.getId());

            // Aquí deberías obtener los datos de tu base de datos, si los necesitas
            // Establecer el estilo de fuente para el contenido
            Font contentFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 14, Font.BOLD);
            //Font atributos = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.NORMAL, BaseColor.DARK_GRAY);
            Font contentFont1 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.BOLD);
            Font contentFont2 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.NORMAL);
            Font contentFont3 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.BOLD);
            Font contentFont4 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 11, Font.NORMAL);
            Font contentFont5 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 10, Font.NORMAL);
            // Agregar contenido al PDF
            // Crear un párrafo vacío antes del título para agregar espacio entre el título y el primer párrafo
            //Crear Fecha para portada
            Date currentDate = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM yyyy");
            String formattedDate = dateFormat.format(currentDate).toUpperCase();

            Font titleFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 16, Font.BOLD);
            Paragraph title = new Paragraph(client.getCompanyName().toUpperCase(), titleFont);
            title.setSpacingBefore(20f);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(new Paragraph("\n"));
            Paragraph title1 = new Paragraph("Diagnóstico Empresarial- Gestión de la Innovación", titleFont);

            title1.setAlignment(Element.ALIGN_CENTER);
            document.add(title1);
            document.add(new Paragraph("\n"));

            // Crear una tabla con 4 columnas
            PdfPTable table = new PdfPTable(4);

            // Configurar el ancho de las columnas
            float[] columnWidths = {4, 20, 60, 14};
            table.setWidths(columnWidths);
            BaseColor colorHeader = new BaseColor(204, 255, 255);
            // Agregar encabezados de columna
            PdfPCell cell = new PdfPCell();

            cell = new PdfPCell();
            cell.setPhrase(new Phrase("5. GESTIÓN DE LA INNOVACIÓN", contentFont4));
            cell.setBackgroundColor(colorHeader);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setColspan(3);
            table.addCell(cell);

            cell = new PdfPCell();
            cell.setPhrase(new Phrase("Calificación", contentFont4));
            cell.setBackgroundColor(colorHeader);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table.addCell(cell);

            List<Integer> esterategica = processEmpresario.getDiagnosticoEmpresarial().getDiagnostico().getGestionInnovacion();
            List<Float> totales = processEmpresario.getDiagnosticoEmpresarial().getDiagnostico().getTotales();
            // Agregar filas de datos (esto es un ejemplo, deberás adaptar los datos según tus necesidades)
            for (int i = 0; i < processEmpresario.getDiagnosticoEmpresarial().getDiagnostico().getGestionInnovacion().size(); i++) {
                Integer concepto = esterategica.get(i);
                PdfPCell cell1 = new PdfPCell(new Phrase(Integer.toString(i + 1), contentFont4));
                PdfPCell cell2 = new PdfPCell(new Phrase(elementos[i], contentFont5));
                PdfPCell cell3 = new PdfPCell(new Phrase(preguntas[i], contentFont4));
                PdfPCell cell4 = new PdfPCell(new Phrase(new DecimalFormat("#.##").format(concepto), contentFont5));

                // Establecer la alineación de todas las celdas
                cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell4.setVerticalAlignment(Element.ALIGN_MIDDLE);

                // Agregar las celdas a la tabla
                table.addCell(cell1);
                table.addCell(cell2);
                table.addCell(cell3);
                table.addCell(cell4);
            }

            BaseColor colorHeader1 = new BaseColor(255, 255, 204);
            cell = new PdfPCell();
            cell.setPhrase(new Phrase("PUNTAJE TOTAL", contentFont4));
            cell.setBackgroundColor(colorHeader1);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setColspan(3);
            table.addCell(cell);

            cell = new PdfPCell();
            cell.setPhrase(new Phrase(new DecimalFormat("#.##").format(totales.get(4)), contentFont4));
            cell.setBackgroundColor(colorHeader1);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setColspan(3);
            table.addCell(cell);

            // Agregar la tabla al documento
            document.add(table);

            // Cerrar el documento
            document.close();
            outputStream.flush();
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
            // Manejar errores aquí
        }
    }

    @GetMapping("/financiera/{id}")
    public void generarFinanciera(HttpServletResponse response, @PathVariable Long id) {
        String[] elementos = {
            "Normatividad",
            "Presupuestos",
            "Información Financiera",
            "Sistema Contable",
            "Utilidades",
            "Proveedores",
            "Acreedores",
            "Crecimiento",
            "Contabilidad",
            "Punto de Equilibrio",
            "Fuentes de Financiamiento",
            "Informes Financieros",
            "Indicadores",
            "Liquidez",
            "Conocimiento Sector",
            "Estrategia Financiera"
        };

        String[] preguntas = {
            "¿La empresa conoce y aplica las normas contables vigentes?",
            "¿La empresa realiza presupuestos anuales de ingresos, egresos y flujo de caja?",
            "¿La información financiera de la empresa es confiable, oportuna, útil y se usa para la toma de decisiones?",
            "¿La empresa cuenta con un sistema claro para establecer sus costos, dependiendo de los productos, servicios y procesos?",
            "¿La empresa conoce la productividad que le genera la inversión en activos y el impacto de estos en la generación de utilidades en el negocio?",
            "¿La empresa tiene una política definida para el pago a sus proveedores?",
            "¿La empresa cumple con los compromisos adquiridos con sus acreedores de manera oportuna?",
            "¿La empresa evalúa el crecimiento del negocio frente a las inversiones realizadas y conoce el retorno sobre su inversión?",
            "¿La empresa maneja contabilidad al día?",
            "¿La Empresa conoce y aplica el concepto de punto de equilibrio?",
            "¿Conocen otras fuentes de financiación diferentes a las entidades bancarias?",
            "¿La Empresa realiza informes periódicos del estado financiero?",
            "¿La empresa conoce y aplica indicadores financieros? (Liquidez, Razón Cta., Prueba ácida, etc.)",
            "¿La empresa cuenta con políticas para los excesos de liquidez?",
            "¿La Empresa conoce indicadores de comportamiento del sector económico de su actividad empresarial?",
            "¿La empresa cuenta con metas financieras medibles y verificables en un plazo de tiempo definido, con asignación del responsable de su cumplimiento?"
        };

        // Establecer el tipo de contenido de la respuesta como PDF
        response.setContentType("application/pdf");
        // Establecer el encabezado para indicar la descarga del archivo PDF
        response.setHeader("Content-Disposition", "attachment; filename=gestionFinanciera.pdf");

        try {
            // Crear una instancia del documento y del escritor
            Document document = new Document(PageSize.A4);
            OutputStream outputStream = response.getOutputStream();
            PdfWriter writer = PdfWriter.getInstance(document, outputStream);
            document.setMargins(0, 0, 70, 30);

            // Crear una instancia de la clase PdfFooterEventHandler para manejar el pie de página
            //PdfFooterEventHandler footerHandler = new PdfFooterEventHandler();
            //writer.setPageEvent(footerHandler);
            // Crear una instancia de la clase PdfHeaderEventHandler para manejar el encabezado
            PdfHeaderEventHandler1 headerHandler = new PdfHeaderEventHandler1();
            writer.setPageEvent(headerHandler);

            // Abrir el documento para escribir el contenido
            document.open();
            Client client = clientRepository.findById(id).get();
            ProcessEmpresario processEmpresario = processEmpresarioRepository.findByClient(client.getId());

            // Aquí deberías obtener los datos de tu base de datos, si los necesitas
            // Establecer el estilo de fuente para el contenido
            Font contentFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 14, Font.BOLD);
            //Font atributos = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.NORMAL, BaseColor.DARK_GRAY);
            Font contentFont1 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.BOLD);
            Font contentFont2 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.NORMAL);
            Font contentFont3 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.BOLD);
            Font contentFont4 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 11, Font.NORMAL);
            Font contentFont5 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 10, Font.NORMAL);
            // Agregar contenido al PDF
            // Crear un párrafo vacío antes del título para agregar espacio entre el título y el primer párrafo
            //Crear Fecha para portada
            Date currentDate = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM yyyy");
            String formattedDate = dateFormat.format(currentDate).toUpperCase();

            Font titleFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 16, Font.BOLD);
            Paragraph title = new Paragraph(client.getCompanyName().toUpperCase(), titleFont);
            title.setSpacingBefore(20f);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(new Paragraph("\n"));
            Paragraph title1 = new Paragraph("Diagnóstico Empresarial- Gestión Financiera y Contable", titleFont);

            title1.setAlignment(Element.ALIGN_CENTER);
            document.add(title1);
            document.add(new Paragraph("\n"));

            // Crear una tabla con 4 columnas
            PdfPTable table = new PdfPTable(4);

            // Configurar el ancho de las columnas
            float[] columnWidths = {4, 20, 60, 14};
            table.setWidths(columnWidths);
            BaseColor colorHeader = new BaseColor(204, 255, 255);
            // Agregar encabezados de columna
            PdfPCell cell = new PdfPCell();

            cell = new PdfPCell();
            cell.setPhrase(new Phrase("6. GESTIÓN FINANCIERA Y CONTABLE", contentFont4));
            cell.setBackgroundColor(colorHeader);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setColspan(3);
            table.addCell(cell);

            cell = new PdfPCell();
            cell.setPhrase(new Phrase("Calificación", contentFont4));
            cell.setBackgroundColor(colorHeader);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table.addCell(cell);

            List<Integer> esterategica = processEmpresario.getDiagnosticoEmpresarial().getDiagnostico().getGestionFinanciera();
            List<Float> totales = processEmpresario.getDiagnosticoEmpresarial().getDiagnostico().getTotales();
            // Agregar filas de datos (esto es un ejemplo, deberás adaptar los datos según tus necesidades)
            for (int i = 0; i < processEmpresario.getDiagnosticoEmpresarial().getDiagnostico().getGestionFinanciera().size(); i++) {
                Integer concepto = esterategica.get(i);
                PdfPCell cell1 = new PdfPCell(new Phrase(Integer.toString(i + 1), contentFont4));
                PdfPCell cell2 = new PdfPCell(new Phrase(elementos[i], contentFont5));
                PdfPCell cell3 = new PdfPCell(new Phrase(preguntas[i], contentFont4));
                PdfPCell cell4 = new PdfPCell(new Phrase(new DecimalFormat("#.##").format(concepto), contentFont5));

                // Establecer la alineación de todas las celdas
                cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell4.setVerticalAlignment(Element.ALIGN_MIDDLE);

                // Agregar las celdas a la tabla
                table.addCell(cell1);
                table.addCell(cell2);
                table.addCell(cell3);
                table.addCell(cell4);
            }

            BaseColor colorHeader1 = new BaseColor(255, 255, 204);
            cell = new PdfPCell();
            cell.setPhrase(new Phrase("PUNTAJE TOTAL", contentFont4));
            cell.setBackgroundColor(colorHeader1);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setColspan(3);
            table.addCell(cell);

            cell = new PdfPCell();
            cell.setPhrase(new Phrase(new DecimalFormat("#.##").format(totales.get(5)), contentFont4));
            cell.setBackgroundColor(colorHeader1);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setColspan(3);
            table.addCell(cell);

            // Agregar la tabla al documento
            document.add(table);

            // Cerrar el documento
            document.close();
            outputStream.flush();
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
            // Manejar errores aquí
        }
    }

    @GetMapping("/logistica/{id}")
    public void generarLogistica(HttpServletResponse response, @PathVariable Long id) {
        String[] elementos = {
            "Inventarios",
            "Distribución",
            "Proveedores",
            "Logística",
            "Proceso Logístico",
            "Proveedores"
        };

        String[] preguntas = {
            "¿Cuentan con un sistema para la administración de inventarios, materia prima y producto terminado?",
            "¿La empresa cuenta con un responsable para la gestión de compras, transporte y distribución?",
            "¿La empresa cuenta con un proceso de evaluación y desarrollo de proveedores?",
            "¿La empresa cuenta con una infraestructura idónea para optimizar los costos de logística?",
            "¿La empresa cuenta con un proceso logístico?",
            "¿En la empresa existen criterios definidos para selección y evaluación de proveedores?"

        };

        // Establecer el tipo de contenido de la respuesta como PDF
        response.setContentType("application/pdf");
        // Establecer el encabezado para indicar la descarga del archivo PDF
        response.setHeader("Content-Disposition", "attachment; filename=gestionLogistica.pdf");

        try {
            // Crear una instancia del documento y del escritor
            Document document = new Document(PageSize.A4);
            OutputStream outputStream = response.getOutputStream();
            PdfWriter writer = PdfWriter.getInstance(document, outputStream);
            document.setMargins(0, 0, 70, 30);

            // Crear una instancia de la clase PdfFooterEventHandler para manejar el pie de página
            //PdfFooterEventHandler footerHandler = new PdfFooterEventHandler();
            //writer.setPageEvent(footerHandler);
            // Crear una instancia de la clase PdfHeaderEventHandler para manejar el encabezado
            PdfHeaderEventHandler1 headerHandler = new PdfHeaderEventHandler1();
            writer.setPageEvent(headerHandler);

            // Abrir el documento para escribir el contenido
            document.open();
            Client client = clientRepository.findById(id).get();
            ProcessEmpresario processEmpresario = processEmpresarioRepository.findByClient(client.getId());

            // Aquí deberías obtener los datos de tu base de datos, si los necesitas
            // Establecer el estilo de fuente para el contenido
            Font contentFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 14, Font.BOLD);
            //Font atributos = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.NORMAL, BaseColor.DARK_GRAY);
            Font contentFont1 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.BOLD);
            Font contentFont2 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.NORMAL);
            Font contentFont3 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.BOLD);
            Font contentFont4 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 11, Font.NORMAL);
            Font contentFont5 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 10, Font.NORMAL);
            // Agregar contenido al PDF
            // Crear un párrafo vacío antes del título para agregar espacio entre el título y el primer párrafo
            //Crear Fecha para portada
            Date currentDate = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM yyyy");
            String formattedDate = dateFormat.format(currentDate).toUpperCase();

            Font titleFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 16, Font.BOLD);
            Paragraph title = new Paragraph(client.getCompanyName().toUpperCase(), titleFont);
            title.setSpacingBefore(20f);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(new Paragraph("\n"));
            Paragraph title1 = new Paragraph("Diagnóstico Empresarial- Gestión Logística", titleFont);

            title1.setAlignment(Element.ALIGN_CENTER);
            document.add(title1);
            document.add(new Paragraph("\n"));

            // Crear una tabla con 4 columnas
            PdfPTable table = new PdfPTable(4);

            // Configurar el ancho de las columnas
            float[] columnWidths = {4, 20, 60, 14};
            table.setWidths(columnWidths);
            BaseColor colorHeader = new BaseColor(204, 255, 255);
            // Agregar encabezados de columna
            PdfPCell cell = new PdfPCell();

            cell = new PdfPCell();
            cell.setPhrase(new Phrase("7. GESTIÓN LOGÍSTICA", contentFont4));
            cell.setBackgroundColor(colorHeader);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setColspan(3);
            table.addCell(cell);

            cell = new PdfPCell();
            cell.setPhrase(new Phrase("Calificación", contentFont4));
            cell.setBackgroundColor(colorHeader);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table.addCell(cell);

            List<Integer> esterategica = processEmpresario.getDiagnosticoEmpresarial().getDiagnostico().getGestionLogistica();
            List<Float> totales = processEmpresario.getDiagnosticoEmpresarial().getDiagnostico().getTotales();
            // Agregar filas de datos (esto es un ejemplo, deberás adaptar los datos según tus necesidades)
            for (int i = 0; i < processEmpresario.getDiagnosticoEmpresarial().getDiagnostico().getGestionLogistica().size(); i++) {
                Integer concepto = esterategica.get(i);
                PdfPCell cell1 = new PdfPCell(new Phrase(Integer.toString(i + 1), contentFont4));
                PdfPCell cell2 = new PdfPCell(new Phrase(elementos[i], contentFont5));
                PdfPCell cell3 = new PdfPCell(new Phrase(preguntas[i], contentFont4));
                PdfPCell cell4 = new PdfPCell(new Phrase(new DecimalFormat("#.##").format(concepto), contentFont5));

                // Establecer la alineación de todas las celdas
                cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell4.setVerticalAlignment(Element.ALIGN_MIDDLE);

                // Agregar las celdas a la tabla
                table.addCell(cell1);
                table.addCell(cell2);
                table.addCell(cell3);
                table.addCell(cell4);
            }

            BaseColor colorHeader1 = new BaseColor(255, 255, 204);
            cell = new PdfPCell();
            cell.setPhrase(new Phrase("PUNTAJE TOTAL", contentFont4));
            cell.setBackgroundColor(colorHeader1);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setColspan(3);
            table.addCell(cell);

            cell = new PdfPCell();
            cell.setPhrase(new Phrase(new DecimalFormat("#.##").format(totales.get(6)), contentFont4));
            cell.setBackgroundColor(colorHeader1);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setColspan(3);
            table.addCell(cell);

            // Agregar la tabla al documento
            document.add(table);

            // Cerrar el documento
            document.close();
            outputStream.flush();
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
            // Manejar errores aquí
        }
    }

    @GetMapping("/transformacion/{id}")
    public void generarTransformacion(HttpServletResponse response, @PathVariable Long id) {
        String[] elementos = {
            "Digitalización",
            "Transformación",
            "Digitalización",
            "Almacenamiento de datos",
            "Protección de datos",
            "Mejora productos"
        };

        String[] preguntas = {
            "¿La empresa cuenta con herramientas digitales y tecnológicas para mejorar la producción o prestación del servicio?",
            "¿La empresa ha considerado redefinir su nuevo modelo de negocio?",
            "¿La empresa tiene acceso al marketing digital (redes sociales, página web, tiendas virtuales)?",
            "¿La empresa hace uso o tiene acceso a la tecnología de big data, nube?",
            "¿La empresa cuenta con un sistema de protección de datos?",
            "¿La empresa ha buscado expertos para la transformación tecnológica de sus productos y/o servicios?"

        };

        // Establecer el tipo de contenido de la respuesta como PDF
        response.setContentType("application/pdf");
        // Establecer el encabezado para indicar la descarga del archivo PDF
        response.setHeader("Content-Disposition", "attachment; filename=gestionTransformacion.pdf");

        try {
            // Crear una instancia del documento y del escritor
            Document document = new Document(PageSize.A4);
            OutputStream outputStream = response.getOutputStream();
            PdfWriter writer = PdfWriter.getInstance(document, outputStream);
            document.setMargins(0, 0, 70, 30);

            // Crear una instancia de la clase PdfFooterEventHandler para manejar el pie de página
            //PdfFooterEventHandler footerHandler = new PdfFooterEventHandler();
            //writer.setPageEvent(footerHandler);
            // Crear una instancia de la clase PdfHeaderEventHandler para manejar el encabezado
            PdfHeaderEventHandler1 headerHandler = new PdfHeaderEventHandler1();
            writer.setPageEvent(headerHandler);

            // Abrir el documento para escribir el contenido
            document.open();
            Client client = clientRepository.findById(id).get();
            ProcessEmpresario processEmpresario = processEmpresarioRepository.findByClient(client.getId());

            // Aquí deberías obtener los datos de tu base de datos, si los necesitas
            // Establecer el estilo de fuente para el contenido
            Font contentFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 14, Font.BOLD);
            //Font atributos = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.NORMAL, BaseColor.DARK_GRAY);
            Font contentFont1 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.BOLD);
            Font contentFont2 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.NORMAL);
            Font contentFont3 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.BOLD);
            Font contentFont4 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 11, Font.NORMAL);
            Font contentFont5 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 10, Font.NORMAL);
            // Agregar contenido al PDF
            // Crear un párrafo vacío antes del título para agregar espacio entre el título y el primer párrafo
            //Crear Fecha para portada
            Date currentDate = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM yyyy");
            String formattedDate = dateFormat.format(currentDate).toUpperCase();

            Font titleFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 16, Font.BOLD);
            Paragraph title = new Paragraph(client.getCompanyName().toUpperCase(), titleFont);
            title.setSpacingBefore(20f);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(new Paragraph("\n"));
            Paragraph title1 = new Paragraph("Diagnóstico Empresarial- Gestión de Transformación Digítal", titleFont);

            title1.setAlignment(Element.ALIGN_CENTER);
            document.add(title1);
            document.add(new Paragraph("\n"));

            // Crear una tabla con 4 columnas
            PdfPTable table = new PdfPTable(4);

            // Configurar el ancho de las columnas
            float[] columnWidths = {4, 20, 60, 14};
            table.setWidths(columnWidths);
            BaseColor colorHeader = new BaseColor(204, 255, 255);
            // Agregar encabezados de columna
            PdfPCell cell = new PdfPCell();

            cell = new PdfPCell();
            cell.setPhrase(new Phrase("8. GESTIÓN DE TRANSFORMACIÓN DIGÍTAL", contentFont4));
            cell.setBackgroundColor(colorHeader);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setColspan(3);
            table.addCell(cell);

            cell = new PdfPCell();
            cell.setPhrase(new Phrase("Calificación", contentFont4));
            cell.setBackgroundColor(colorHeader);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table.addCell(cell);

            List<Integer> esterategica = processEmpresario.getDiagnosticoEmpresarial().getDiagnostico().getGestionDigital();
            List<Float> totales = processEmpresario.getDiagnosticoEmpresarial().getDiagnostico().getTotales();
            // Agregar filas de datos (esto es un ejemplo, deberás adaptar los datos según tus necesidades)
            for (int i = 0; i < processEmpresario.getDiagnosticoEmpresarial().getDiagnostico().getGestionDigital().size(); i++) {
                Integer concepto = esterategica.get(i);
                PdfPCell cell1 = new PdfPCell(new Phrase(Integer.toString(i + 1), contentFont4));
                PdfPCell cell2 = new PdfPCell(new Phrase(elementos[i], contentFont5));
                PdfPCell cell3 = new PdfPCell(new Phrase(preguntas[i], contentFont4));
                PdfPCell cell4 = new PdfPCell(new Phrase(concepto.toString(), contentFont5));

                // Establecer la alineación de todas las celdas
                cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell4.setVerticalAlignment(Element.ALIGN_MIDDLE);

                // Agregar las celdas a la tabla
                table.addCell(cell1);
                table.addCell(cell2);
                table.addCell(cell3);
                table.addCell(cell4);
            }

            BaseColor colorHeader1 = new BaseColor(255, 255, 204);
            cell = new PdfPCell();
            cell.setPhrase(new Phrase("PUNTAJE TOTAL", contentFont4));
            cell.setBackgroundColor(colorHeader1);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setColspan(3);
            table.addCell(cell);

            cell = new PdfPCell();
            cell.setPhrase(new Phrase(new DecimalFormat("#.##").format(totales.get(7)), contentFont4));
            cell.setBackgroundColor(colorHeader1);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setColspan(3);
            table.addCell(cell);

            // Agregar la tabla al documento
            document.add(table);

            // Cerrar el documento
            document.close();
            outputStream.flush();
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
            // Manejar errores aquí
        }
    }

    @GetMapping("/ambiental/{id}")
    public void generarAmbiental(HttpServletResponse response, @PathVariable Long id) {
        String[] elementos = {
            "Normatividad",
            "Cumplimiento",
            "Producción",
            "Mantenimiento",
            "Prácticas",
            "Costos",
            "Diseño",
            "Vida Útil",
            "Producción Limpia"
        };

        String[] preguntas = {
            "¿La empresa conoce y aplica las normas ambientales en el desarrollo de su actividad?",
            "¿La empresa establece los procedimientos y procesos para cumplir las normas ambientales?",
            "¿La empresa trata de minimizar el consumo de energía, agua y materias primas contaminantes mediante la mejora de sus procesos productivos, la sustitución de insumos y el uso de otras tecnologías?",
            "¿La empresa realiza mantenimientos preventivos a sus automotores, maquinaria y equipos para evitar el deterioro del ambiente?",
            "¿La empresa desarrolla prácticas de reciclaje en forma permanente y todos los empleados están comprometidos con este propósito?",
            "¿La empresa tiene o ha proyectado disminuir los costos generados por desperdicio de materia prima en alguna etapa del proceso productivo?",
            "¿En el diseño de productos y empaques, la empresa procura minimizar el uso de materiales e incluye criterios ecológicos al seleccionarlas?",
            "¿En la empresa se fomenta el aprovechamiento de los productos al final de su vida útil (por ejemplo, mediante reutilización, procesamiento o reciclaje)?",
            "¿La Empresa aplica conceptos de producción limpia?"

        };

        // Establecer el tipo de contenido de la respuesta como PDF
        response.setContentType("application/pdf");
        // Establecer el encabezado para indicar la descarga del archivo PDF
        response.setHeader("Content-Disposition", "attachment; filename=gestionAmbiental.pdf");

        try {
            // Crear una instancia del documento y del escritor
            Document document = new Document(PageSize.A4);
            OutputStream outputStream = response.getOutputStream();
            PdfWriter writer = PdfWriter.getInstance(document, outputStream);
            document.setMargins(0, 0, 70, 30);

            // Crear una instancia de la clase PdfFooterEventHandler para manejar el pie de página
            //PdfFooterEventHandler footerHandler = new PdfFooterEventHandler();
            //writer.setPageEvent(footerHandler);
            // Crear una instancia de la clase PdfHeaderEventHandler para manejar el encabezado
            PdfHeaderEventHandler1 headerHandler = new PdfHeaderEventHandler1();
            writer.setPageEvent(headerHandler);

            // Abrir el documento para escribir el contenido
            document.open();
            Client client = clientRepository.findById(id).get();
            ProcessEmpresario processEmpresario = processEmpresarioRepository.findByClient(client.getId());

            // Aquí deberías obtener los datos de tu base de datos, si los necesitas
            // Establecer el estilo de fuente para el contenido
            Font contentFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 14, Font.BOLD);
            //Font atributos = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.NORMAL, BaseColor.DARK_GRAY);
            Font contentFont1 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.BOLD);
            Font contentFont2 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.NORMAL);
            Font contentFont3 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.BOLD);
            Font contentFont4 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 11, Font.NORMAL);
            Font contentFont5 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 10, Font.NORMAL);
            // Agregar contenido al PDF
            // Crear un párrafo vacío antes del título para agregar espacio entre el título y el primer párrafo
            //Crear Fecha para portada
            Date currentDate = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM yyyy");
            String formattedDate = dateFormat.format(currentDate).toUpperCase();

            Font titleFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 16, Font.BOLD);
            Paragraph title = new Paragraph(client.getCompanyName().toUpperCase(), titleFont);
            title.setSpacingBefore(20f);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(new Paragraph("\n"));
            Paragraph title1 = new Paragraph("Diagnóstico Empresarial- Gestión de Sostenibilidad Ambiental", titleFont);

            title1.setAlignment(Element.ALIGN_CENTER);
            document.add(title1);
            document.add(new Paragraph("\n"));

            // Crear una tabla con 4 columnas
            PdfPTable table = new PdfPTable(4);

            // Configurar el ancho de las columnas
            float[] columnWidths = {4, 20, 60, 14};
            table.setWidths(columnWidths);
            BaseColor colorHeader = new BaseColor(204, 255, 255);
            // Agregar encabezados de columna
            PdfPCell cell = new PdfPCell();

            cell = new PdfPCell();
            cell.setPhrase(new Phrase("9. GESTIÓN DE SOSTENIBILIDAD AMBIENTAL", contentFont4));
            cell.setBackgroundColor(colorHeader);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setColspan(3);
            table.addCell(cell);

            cell = new PdfPCell();
            cell.setPhrase(new Phrase("Calificación", contentFont4));
            cell.setBackgroundColor(colorHeader);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table.addCell(cell);

            List<Integer> esterategica = processEmpresario.getDiagnosticoEmpresarial().getDiagnostico().getGestionAmbiental();
            List<Float> totales = processEmpresario.getDiagnosticoEmpresarial().getDiagnostico().getTotales();
            // Agregar filas de datos (esto es un ejemplo, deberás adaptar los datos según tus necesidades)
            for (int i = 0; i < processEmpresario.getDiagnosticoEmpresarial().getDiagnostico().getGestionAmbiental().size(); i++) {
                Integer concepto = esterategica.get(i);
                PdfPCell cell1 = new PdfPCell(new Phrase(Integer.toString(i + 1), contentFont4));
                PdfPCell cell2 = new PdfPCell(new Phrase(elementos[i], contentFont5));
                PdfPCell cell3 = new PdfPCell(new Phrase(preguntas[i], contentFont4));
                PdfPCell cell4 = new PdfPCell(new Phrase(concepto.toString(), contentFont5));

                // Establecer la alineación de todas las celdas
                cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell4.setVerticalAlignment(Element.ALIGN_MIDDLE);

                // Agregar las celdas a la tabla
                table.addCell(cell1);
                table.addCell(cell2);
                table.addCell(cell3);
                table.addCell(cell4);
            }

            BaseColor colorHeader1 = new BaseColor(255, 255, 204);
            cell = new PdfPCell();
            cell.setPhrase(new Phrase("PUNTAJE TOTAL", contentFont4));
            cell.setBackgroundColor(colorHeader1);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setColspan(3);
            table.addCell(cell);

            cell = new PdfPCell();
            cell.setPhrase(new Phrase(new DecimalFormat("#.##").format(totales.get(8)), contentFont4));
            cell.setBackgroundColor(colorHeader1);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setColspan(3);
            table.addCell(cell);

            // Agregar la tabla al documento
            document.add(table);

            // Cerrar el documento
            document.close();
            outputStream.flush();
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
            // Manejar errores aquí
        }
    }

    @GetMapping("/intelectual/{id}")
    public void generarIntelectual(HttpServletResponse response, @PathVariable Long id) {
        String[] elementos = {
            "Derecho de Autor",
            "Derecho Conexos",
            "Propiedad Intelectual",
            "Patente",
            "Diseño Industrial",
            "Marca"
        };

        String[] preguntas = {
            "¿La empresa ha hecho gestión de los derechos de autor?",
            "¿La empresa ha hecho gestión de los derechos conexos?",
            "¿La empresa ha hecho gestión de propiedad intelectual?",
            "¿La empresa cuenta con una patente?",
            "¿La empresa cuenta con un diseño industrial?",
            "¿La empresa cuenta con una marca?"

        };

        // Establecer el tipo de contenido de la respuesta como PDF
        response.setContentType("application/pdf");
        // Establecer el encabezado para indicar la descarga del archivo PDF
        response.setHeader("Content-Disposition", "attachment; filename=gestionIntelectual.pdf");

        try {
            // Crear una instancia del documento y del escritor
            Document document = new Document(PageSize.A4);
            OutputStream outputStream = response.getOutputStream();
            PdfWriter writer = PdfWriter.getInstance(document, outputStream);
            document.setMargins(0, 0, 70, 30);

            // Crear una instancia de la clase PdfFooterEventHandler para manejar el pie de página
            //PdfFooterEventHandler footerHandler = new PdfFooterEventHandler();
            //writer.setPageEvent(footerHandler);
            // Crear una instancia de la clase PdfHeaderEventHandler para manejar el encabezado
            PdfHeaderEventHandler1 headerHandler = new PdfHeaderEventHandler1();
            writer.setPageEvent(headerHandler);

            // Abrir el documento para escribir el contenido
            document.open();
            Client client = clientRepository.findById(id).get();
            ProcessEmpresario processEmpresario = processEmpresarioRepository.findByClient(client.getId());

            // Aquí deberías obtener los datos de tu base de datos, si los necesitas
            // Establecer el estilo de fuente para el contenido
            Font contentFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 14, Font.BOLD);
            //Font atributos = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.NORMAL, BaseColor.DARK_GRAY);
            Font contentFont1 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.BOLD);
            Font contentFont2 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.NORMAL);
            Font contentFont3 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.BOLD);
            Font contentFont4 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 11, Font.NORMAL);
            Font contentFont5 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 10, Font.NORMAL);
            // Agregar contenido al PDF
            // Crear un párrafo vacío antes del título para agregar espacio entre el título y el primer párrafo
            //Crear Fecha para portada
            Date currentDate = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM yyyy");
            String formattedDate = dateFormat.format(currentDate).toUpperCase();

            Font titleFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 16, Font.BOLD);
            Paragraph title = new Paragraph(client.getCompanyName().toUpperCase(), titleFont);
            title.setSpacingBefore(20f);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(new Paragraph("\n"));
            Paragraph title1 = new Paragraph("Diagnóstico Empresarial- Gestión de la Propiedad Intelectual e Industrial", titleFont);

            title1.setAlignment(Element.ALIGN_CENTER);
            document.add(title1);
            document.add(new Paragraph("\n"));

            // Crear una tabla con 4 columnas
            PdfPTable table = new PdfPTable(4);

            // Configurar el ancho de las columnas
            float[] columnWidths = {4, 20, 60, 14};
            table.setWidths(columnWidths);
            BaseColor colorHeader = new BaseColor(204, 255, 255);
            // Agregar encabezados de columna
            PdfPCell cell = new PdfPCell();

            cell = new PdfPCell();
            cell.setPhrase(new Phrase("10. GESTIÓN DE LA  PROPIEDAD INTELECTUAL E INDUSTRIAL", contentFont4));
            cell.setBackgroundColor(colorHeader);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setColspan(3);
            table.addCell(cell);

            cell = new PdfPCell();
            cell.setPhrase(new Phrase("Calificación", contentFont4));
            cell.setBackgroundColor(colorHeader);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table.addCell(cell);

            List<Integer> esterategica = processEmpresario.getDiagnosticoEmpresarial().getDiagnostico().getGestionAmbiental();
            List<Float> totales = processEmpresario.getDiagnosticoEmpresarial().getDiagnostico().getTotales();
            // Agregar filas de datos (esto es un ejemplo, deberás adaptar los datos según tus necesidades)
            for (int i = 0; i < processEmpresario.getDiagnosticoEmpresarial().getDiagnostico().getGestionAmbiental().size(); i++) {
                Integer concepto = esterategica.get(i);
                PdfPCell cell1 = new PdfPCell(new Phrase(Integer.toString(i + 1), contentFont4));
                PdfPCell cell2 = new PdfPCell(new Phrase(elementos[i], contentFont5));
                PdfPCell cell3 = new PdfPCell(new Phrase(preguntas[i], contentFont4));
                PdfPCell cell4 = new PdfPCell(new Phrase(concepto.toString(), contentFont5));

                // Establecer la alineación de todas las celdas
                cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell4.setVerticalAlignment(Element.ALIGN_MIDDLE);

                // Agregar las celdas a la tabla
                table.addCell(cell1);
                table.addCell(cell2);
                table.addCell(cell3);
                table.addCell(cell4);
            }

            BaseColor colorHeader1 = new BaseColor(255, 255, 204);
            cell = new PdfPCell();
            cell.setPhrase(new Phrase("PUNTAJE TOTAL", contentFont4));
            cell.setBackgroundColor(colorHeader1);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setColspan(3);
            table.addCell(cell);

            cell = new PdfPCell();
            cell.setPhrase(new Phrase(new DecimalFormat("#.##").format(totales.get(9)), contentFont4));
            cell.setBackgroundColor(colorHeader1);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setColspan(3);
            table.addCell(cell);

            // Agregar la tabla al documento
            document.add(table);

            // Cerrar el documento
            document.close();
            outputStream.flush();
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
            // Manejar errores aquí
        }
    }

    @GetMapping("/analisisEconomico/{id}")//
    public void generarPlanAccion(HttpServletResponse response, @PathVariable Long id) {

        try {
            // Inicializar las imágenes en el constructor
            String imagePathIzq = new File("ImagenCamaraComercioIzquierda.jpg").getAbsolutePath();
            headerImageIzq = Image.getInstance(imagePathIzq);
            headerImageIzq.scaleToFit(150, headerImageIzq.getHeight());

            String imagePathDer = new File("CodigoRCP-HerramientaDiagnostico.jpg").getAbsolutePath();
            headerImageDer = Image.getInstance(imagePathDer);
            headerImageDer.scaleToFit(100, 100);

            String imagePathIzq3 = new File("ImagenCDE_Empresarial.jpg").getAbsolutePath();
            headerImageIzq3 = Image.getInstance(imagePathIzq3);
            headerImageIzq3.scaleToFit(150, 150);
        } catch (IOException | DocumentException e) {
            e.printStackTrace();
        }
        // Establecer el tipo de contenido de la respuesta como PDF
        response.setContentType("application/pdf");
        // Establecer el encabezado para indicar la descarga del archivo PDF
        response.setHeader("Content-Disposition", "attachment; filename=analisisEconomico.pdf");

        try {
// Crear una instancia del documento y del escritor
            Document document = new Document(PageSize.A4); //
            OutputStream outputStream = response.getOutputStream();
            PdfWriter writer = PdfWriter.getInstance(document, outputStream);
            document.setMargins(document.leftMargin(), document.rightMargin(), 100, 0);

            // Crear una instancia de la clase PdfHeaderEventHandler1 para manejar el encabezado
            PdfHeaderEventHandler2 headerHandler = new PdfHeaderEventHandler2();
            writer.setPageEvent(headerHandler);

            // Abrir el documento para escribir el contenido
            document.open();

            Client client = clientRepository.findById(id).get();
            ProcessEmpresario processEmpresario = processEmpresarioRepository.findByClient(client.getId());
            Date currentDate = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM yyyy");
            String formattedDate = dateFormat.format(currentDate).toUpperCase();

            Font titleFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 16, Font.BOLD);
            Paragraph title = new Paragraph(client.getCompanyName().toUpperCase(), titleFont);
            title.setSpacingBefore(20f);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(new Paragraph("\n"));
            Paragraph title1 = new Paragraph("Análisis Económico", titleFont);

            title1.setAlignment(Element.ALIGN_CENTER);
            document.add(title1);
            document.add(new Paragraph("\n"));
            // Aquí deberías obtener los datos de tu base de datos, si los necesitas
            // Establecer el estilo de fuente para el contenido
            Font contentFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 14, Font.BOLD);
            //Font atributos = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.NORMAL, BaseColor.DARK_GRAY);
            Font contentFont1 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.BOLD);
            Font contentFont2 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.NORMAL);
            Font contentFont3 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.BOLD);
            Font contentFont4 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 11, Font.NORMAL);

            // Crear una tabla con 3 columnas
            PdfPTable table = new PdfPTable(2);

            // Agregar filas con 2 columnas de datos
            table.addCell("Mes 1");
            table.addCell(processEmpresario.getDiagnosticoEmpresarial().getAnalisisEconomico().getVentasMes().getMes1());
            table.addCell("Mes 2");
            table.addCell(processEmpresario.getDiagnosticoEmpresarial().getAnalisisEconomico().getVentasMes().getMes2());
            table.addCell("Mes 3");
            table.addCell(processEmpresario.getDiagnosticoEmpresarial().getAnalisisEconomico().getVentasMes().getMes3());
            table.addCell("Mes 4");
            table.addCell(processEmpresario.getDiagnosticoEmpresarial().getAnalisisEconomico().getVentasMes().getMes4());
            table.addCell("Mes 5");
            table.addCell(processEmpresario.getDiagnosticoEmpresarial().getAnalisisEconomico().getVentasMes().getMes4());
            document.add(table);
            PdfPTable table1 = new PdfPTable(1);
            // La última columna ocupa el espacio de las 5 filas
            PdfPCell lastColumnCell = new PdfPCell(new Paragraph("Observaciones: " + processEmpresario.getDiagnosticoEmpresarial().getAnalisisEconomico().getVentasMes().getObservaciones()));
            lastColumnCell.setRowspan(5); // Fusionar 5 filas para la última columna
            table1.addCell(lastColumnCell);

            // Agrega la tabla al documento
            document.add(table1);

            document.close();
            outputStream.flush();
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
            // Manejar errores aquí
        }

    }
    
    @GetMapping("/autoevaluacion/{id}")//
    public void generarAutoevaluacion(HttpServletResponse response, @PathVariable Long id) {

        try {
            // Inicializar las imágenes en el constructor
            String imagePathIzq = new File("ImagenCamaraComercioIzquierda.jpg").getAbsolutePath();
            headerImageIzq = Image.getInstance(imagePathIzq);
            headerImageIzq.scaleToFit(150, headerImageIzq.getHeight());

            String imagePathDer = new File("RCP-Auto.jpg").getAbsolutePath();
            headerImageDer = Image.getInstance(imagePathDer);
            headerImageDer.scaleToFit(100, 100);

            String imagePathIzq3 = new File("ImagenCDE_Empresarial.jpg").getAbsolutePath();
            headerImageIzq3 = Image.getInstance(imagePathIzq3);
            headerImageIzq3.scaleToFit(150, 150);
        } catch (IOException | DocumentException e) {
            e.printStackTrace();
        }
        // Establecer el tipo de contenido de la respuesta como PDF
        response.setContentType("application/pdf");
        // Establecer el encabezado para indicar la descarga del archivo PDF
        response.setHeader("Content-Disposition", "attachment; filename=autoevaluacion.pdf");

        try {

            // Crear una instancia del documento y del escritor
            Document document = new Document(PageSize.A4); //
            OutputStream outputStream = response.getOutputStream();
            PdfWriter writer = PdfWriter.getInstance(document, outputStream);
            document.setMargins(document.leftMargin(), document.rightMargin(), 100, 0);

            // Crear una instancia de la clase PdfHeaderEventHandler1 para manejar el encabezado
            PdfHeaderEventHandler3 headerHandler = new PdfHeaderEventHandler3();
            writer.setPageEvent(headerHandler);

            // Abrir el documento para escribir el contenido
            document.open();
            Client client = clientRepository.findById(id).get();
            SelfAssessment selfAssessment = respoSelf.findByClientId(client.id).get();

            Font titleFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 16, Font.BOLD);

            Paragraph title1 = new Paragraph("Autoevaluación", titleFont);
            title1.setAlignment(Element.ALIGN_CENTER);
            document.add(title1);

            Date currentDate = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM yyyy");
            String formattedDate = dateFormat.format(currentDate).toUpperCase();
            document.add(new Paragraph("\n"));
            // Aquí deberías obtener los datos de tu base de datos, si los necesitas
            // Establecer el estilo de fuente para el contenido
            Font contentFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 14, Font.BOLD);
            //Font atributos = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.NORMAL, BaseColor.DARK_GRAY);
            Font contentFont1 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.BOLD);
            Font contentFont2 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.NORMAL);
            Font contentFont3 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.BOLD);
            Font contentFont4 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 11, Font.NORMAL);
            // Crear una tabla para la primera sección
            BaseColor colorHeader = new BaseColor(204, 255, 255);
            PdfPTable table1 = new PdfPTable(2);
            table1.setTotalWidth(new float[]{0.85f, 0.15f}); // Establece el ancho total y las proporciones
            PdfPCell cell1 = new PdfPCell(new Phrase("Para cada afirmación, indique la opción que mejor lo identifique. Para una mejor evaluación, debería contestar todas las preguntas.", contentFont4));
            PdfPCell cell2 = new PdfPCell(new Phrase("Respuesta", contentFont4));
            cell1.setBackgroundColor(colorHeader);
            cell2.setBackgroundColor(colorHeader);

// Alinea el contenido en el centro vertical de las celdas
            cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell2.setHorizontalAlignment(Element.ALIGN_CENTER);

// Agrega las celdas a la tabla
            table1.addCell(cell1);
            table1.addCell(cell2);

            //Array con las preguntas
            String[] preguntas = {
                "Soy persistente, perseverante.",
                "Tengo capital o activos para invertir y estoy dispuesto a perder gran parte de mis ahorros.",
                "Estoy preparado/a, si fuera necesario, a bajar mis estándares de vida hasta que mi negocio sea rentable.",
                "Tengo ideas nuevas y diferentes.",
                "Me adapto a los cambios.",
                "Percibo los problemas como desafíos y oportunidades.",
                "Me recupero rápido de contratiempos personales.",
                "Soy positivo/a y seguro/a de mí mismo/a.",
                "Me gusta tener el control.",
                "Disfruto la competencia.",
                "He estado involucrado/a en un negocio parecido al que quiero empezar.",
                "Tengo amigos y familiares que me pueden ayudar a empezar.",
                "Mi familia y esposa/o apoyan mi decisión y están preparados a soportar el estrés que tendré como consecuencia de mi empresa.",
                "Tengo la resistencia física y la fortaleza emocional para manejar el estrés del trabajo, las horas extras, y el trabajo durante los fines de semana y feriados.",
                "Soy organizado y me gusta planear con antelación.",
                "Me llevo bien con toda clase de gente, desde banqueros hasta empleados.",
                "Tengo buen juicio y seré capaz de emplear a la gente indicada para mi negocio.",
                "Puedo manejar y supervisar empleados para obtener lo mejor de ellos.",
                "Si descubro que no tengo las habilidades básicas o el capital necesario para iniciar mi negocio, estoy dispuesto/a retrasar mis planes hasta que lo adquiera.",
                "Puedo convivir con gente que no me agrada.",
                "Puedo reconocer, admitir y aprender de mis errores.",
                "Soy bueno/a tomando decisiones.",
                "Tengo la habilidad de observar el contexto en el que estoy y darme cuenta de lo que quiere la gente.",
                "Soy buen vendedor/a y puedo vender mis ideas y servicios a otras personas.",
                "Siempre busco formas de hacer las cosas de una mejor manera.",
                "Soy una persona que nunca se da por vencida.",
                "Hago que las cosas sucedan, en vez de esperar a que sucedan.",
                "Busco ayuda, retroalimentación y crítica constructiva para mejorar como persona.",
                "Soy bueno/a para escuchar.",
                "Tengo un buen o muy buen historial de crédito."
            };

            // Añadir filas de datos de la primera sección
            int a = 0;
            for (Answer answer : selfAssessment.getSelfAssessment()) {
                PdfPCell preguntaCell = new PdfPCell(new Phrase(preguntas[a], contentFont4));
                PdfPCell answerCell = new PdfPCell(new Phrase(answer.toString(), contentFont4));

                // Alinea el contenido en el centro vertical de las celdas
                preguntaCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                answerCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                answerCell.setHorizontalAlignment(Element.ALIGN_CENTER);

                table1.addCell(preguntaCell);
                table1.addCell(answerCell);
                a++;
            }

            // Añadir la tabla al documento
            document.add(table1);

            // Crear una tabla para la segunda sección
            PdfPTable table2 = new PdfPTable(1);

            String[] cellContents = {
                "PUNTAJE: " + selfAssessment.getScore(),
                "CALIFIQUE CADA RESPUESTA DE LA SIGUIENTE MANERA",
                "3 puntos por cada \"SI\". 2 puntos por cada \"QUIZÁS\". 0 puntos por cada \"NO\".",
                "SI USTED OBTUVO UNA PUNTUACION ENTRE:",
                "58 y 71. Usted tiene potencial, pero necesita mayor esfuerzo y dedicación para sacar adelante sus proyectos.",
                "72 y 90. Comience su negocio y programe una cita con un asesor del Centro. Usted tiene las condiciones para ser un buen emprendedor."
            };

            for (String cellContent : cellContents) {
                PdfPCell cell = new PdfPCell(new Phrase(cellContent, contentFont4));

                // Alinea el contenido en el centro vertical y horizontal de las celdas
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);

                table2.addCell(cell);
            }

            // Añadir la segunda tabla al documento
            document.add(table2);

            document.close();

            System.out.println("PDF creado exitosamente.");
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
            // Manejar errores aquí
        }

    }

    @GetMapping("/canvasClientes/{id}")//
    public void canvasClientes(HttpServletResponse response, @PathVariable Long id) {
        try {
            // Inicializar las imágenes en el constructor
            String imagePathIzq = new File("ImagenCamaraComercioIzquierda.jpg").getAbsolutePath();
            headerImageIzq = Image.getInstance(imagePathIzq);
            headerImageIzq.scaleToFit(150, headerImageIzq.getHeight());

            String imagePathDer = new File("RCP-Auto.jpg").getAbsolutePath();
            headerImageDer = Image.getInstance(imagePathDer);
            headerImageDer.scaleToFit(100, 100);

            String imagePathIzq3 = new File("ImagenCDE_Empresarial.jpg").getAbsolutePath();
            headerImageIzq3 = Image.getInstance(imagePathIzq3);
            headerImageIzq3.scaleToFit(150, 150);
        } catch (IOException | DocumentException e) {
            e.printStackTrace();
        }

        // Establecer el tipo de contenido de la respuesta como PDF
        response.setContentType("application/pdf");
        // Establecer el encabezado para indicar la descarga del archivo PDF
        response.setHeader("Content-Disposition", "attachment; filename=segmentoClientes.pdf");

        try {

            // Crear una instancia del documento y del escritor
            Document document = new Document(PageSize.A4); //
            OutputStream outputStream = response.getOutputStream();
            PdfWriter writer = PdfWriter.getInstance(document, outputStream);
            document.setMargins(document.leftMargin(), document.rightMargin(), 100, 0);

            // Crear una instancia de la clase PdfHeaderEventHandler1 para manejar el encabezado
            PdfHeaderEventHandler4 headerHandler = new PdfHeaderEventHandler4();
            writer.setPageEvent(headerHandler);

            // Abrir el documento para escribir el contenido
            document.open();

            Client client = clientRepository.findById(id).get();
            SelfAssessment selfAssessment = respoSelf.findByClientId(client.id).get();
            CanvasModel canvas = repoCanva.findCanvasModelByClientId(client.id).get();

            Font titleFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 16, Font.BOLD);

            Paragraph title1 = new Paragraph("Segmento de Clientes", titleFont);
            title1.setAlignment(Element.ALIGN_CENTER);
            document.add(title1);

            Date currentDate = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM yyyy");
            String formattedDate = dateFormat.format(currentDate).toUpperCase();
            document.add(new Paragraph("\n"));
            // Aquí deberías obtener los datos de tu base de datos, si los necesitas
            // Establecer el estilo de fuente para el contenido
            Font contentFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 14, Font.BOLD);
            //Font atributos = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.NORMAL, BaseColor.DARK_GRAY);
            Font contentFont1 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.BOLD);
            Font contentFont2 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.NORMAL);
            Font contentFont3 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.BOLD);
            Font contentFont4 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 11, Font.NORMAL);

            // Datos del formulario
            String demograficas = canvas.getCustomerSegments().getDemograficas();
            String geograficas = canvas.getCustomerSegments().getGeograficas();
            String psicograficas = canvas.getCustomerSegments().getPsicograficas();
            String comportamiento = canvas.getCustomerSegments().getComportanmiento();

// Crear una tabla en el documento PDF
            PdfPTable table = new PdfPTable(2);
            float[] columnWidths = {25f, 75f}; // Establece las proporciones de ancho

            table.setWidths(columnWidths);

// Agregar celdas a la tabla con los datos del formulario
            PdfPCell cell1 = new PdfPCell(new Phrase("Demograficas", contentFont3));
            PdfPCell cell2 = new PdfPCell(new Phrase(demograficas, contentFont4));
            PdfPCell cell3 = new PdfPCell(new Phrase("Geograficas", contentFont3));
            PdfPCell cell4 = new PdfPCell(new Phrase(geograficas, contentFont4));
            PdfPCell cell5 = new PdfPCell(new Phrase("Psicograficas", contentFont3));
            PdfPCell cell6 = new PdfPCell(new Phrase(psicograficas, contentFont4));
            PdfPCell cell7 = new PdfPCell(new Phrase("Comportamiento", contentFont3));
            PdfPCell cell8 = new PdfPCell(new Phrase(comportamiento, contentFont4));

            table.addCell(cell1);
            table.addCell(cell2);
            table.addCell(cell3);
            table.addCell(cell4);
            table.addCell(cell5);
            table.addCell(cell6);
            table.addCell(cell7);
            table.addCell(cell8);

// Agregar la tabla al documento
            document.add(table);

            document.close();

            System.out.println("PDF creado exitosamente.");
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
            // Manejar errores aquí
        }

    }

    @GetMapping("/canvasValor/{id}")//
    public void canvasValor(HttpServletResponse response, @PathVariable Long id) {
        try {
            // Inicializar las imágenes en el constructor
            String imagePathIzq = new File("ImagenCamaraComercioIzquierda.jpg").getAbsolutePath();
            headerImageIzq = Image.getInstance(imagePathIzq);
            headerImageIzq.scaleToFit(150, headerImageIzq.getHeight());

            String imagePathDer = new File("RCP-Auto.jpg").getAbsolutePath();
            headerImageDer = Image.getInstance(imagePathDer);
            headerImageDer.scaleToFit(100, 100);

            String imagePathIzq3 = new File("ImagenCDE_Empresarial.jpg").getAbsolutePath();
            headerImageIzq3 = Image.getInstance(imagePathIzq3);
            headerImageIzq3.scaleToFit(150, 150);
        } catch (IOException | DocumentException e) {
            e.printStackTrace();
        }

        // Establecer el tipo de contenido de la respuesta como PDF
        response.setContentType("application/pdf");
        // Establecer el encabezado para indicar la descarga del archivo PDF
        response.setHeader("Content-Disposition", "attachment; filename=propuestaValor.pdf");

        try {

            // Crear una instancia del documento y del escritor
            Document document = new Document(PageSize.A4); //
            OutputStream outputStream = response.getOutputStream();
            PdfWriter writer = PdfWriter.getInstance(document, outputStream);
            document.setMargins(document.leftMargin(), document.rightMargin(), 100, 0);

            // Crear una instancia de la clase PdfHeaderEventHandler1 para manejar el encabezado
            PdfHeaderEventHandler4 headerHandler = new PdfHeaderEventHandler4();
            writer.setPageEvent(headerHandler);

            // Abrir el documento para escribir el contenido
            document.open();

            Client client = clientRepository.findById(id).get();
            SelfAssessment selfAssessment = respoSelf.findByClientId(client.id).get();
            CanvasModel canvas = repoCanva.findCanvasModelByClientId(client.id).get();

            Font titleFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 16, Font.BOLD);

            Paragraph title1 = new Paragraph("Propuesta de Valor", titleFont);
            title1.setAlignment(Element.ALIGN_CENTER);
            document.add(title1);

            Date currentDate = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM yyyy");
            String formattedDate = dateFormat.format(currentDate).toUpperCase();
            document.add(new Paragraph("\n"));
            // Aquí deberías obtener los datos de tu base de datos, si los necesitas
            // Establecer el estilo de fuente para el contenido
            Font contentFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 14, Font.BOLD);
            //Font atributos = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.NORMAL, BaseColor.DARK_GRAY);
            Font contentFont1 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.BOLD);
            Font contentFont2 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.NORMAL);
            Font contentFont3 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.BOLD);
            Font contentFont4 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 11, Font.NORMAL);

            // Datos del formulario
            String propuesta = canvas.getValuePropositions().getProposition();

// Crear una tabla en el documento PDF
            PdfPTable table = new PdfPTable(2);
            float[] columnWidths = {25f, 75f}; // Establece las proporciones de ancho

            table.setWidths(columnWidths);

// Agregar celdas a la tabla con los datos del formulario
            PdfPCell cell1 = new PdfPCell(new Phrase("Propuesta", contentFont3));
            PdfPCell cell2 = new PdfPCell(new Phrase(propuesta, contentFont4));

            table.addCell(cell1);
            table.addCell(cell2);

// Agregar la tabla al documento
            document.add(table);

            document.close();

            System.out.println("PDF creado exitosamente.");
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
            // Manejar errores aquí
        }

    }

    @GetMapping("/canvasCanales/{id}")//
    public void canvasCanales(HttpServletResponse response, @PathVariable Long id) {
        try {
            // Inicializar las imágenes en el constructor
            String imagePathIzq = new File("ImagenCamaraComercioIzquierda.jpg").getAbsolutePath();
            headerImageIzq = Image.getInstance(imagePathIzq);
            headerImageIzq.scaleToFit(150, headerImageIzq.getHeight());

            String imagePathDer = new File("RCP-Auto.jpg").getAbsolutePath();
            headerImageDer = Image.getInstance(imagePathDer);
            headerImageDer.scaleToFit(100, 100);

            String imagePathIzq3 = new File("ImagenCDE_Empresarial.jpg").getAbsolutePath();
            headerImageIzq3 = Image.getInstance(imagePathIzq3);
            headerImageIzq3.scaleToFit(150, 150);
        } catch (IOException | DocumentException e) {
            e.printStackTrace();
        }

        // Establecer el tipo de contenido de la respuesta como PDF
        response.setContentType("application/pdf");
        // Establecer el encabezado para indicar la descarga del archivo PDF
        response.setHeader("Content-Disposition", "attachment; filename=canales.pdf");

        try {

            // Crear una instancia del documento y del escritor
            Document document = new Document(PageSize.A4); //
            OutputStream outputStream = response.getOutputStream();
            PdfWriter writer = PdfWriter.getInstance(document, outputStream);
            document.setMargins(document.leftMargin(), document.rightMargin(), 100, 0);

            // Crear una instancia de la clase PdfHeaderEventHandler1 para manejar el encabezado
            PdfHeaderEventHandler4 headerHandler = new PdfHeaderEventHandler4();
            writer.setPageEvent(headerHandler);

            // Abrir el documento para escribir el contenido
            document.open();

            Client client = clientRepository.findById(id).get();
            SelfAssessment selfAssessment = respoSelf.findByClientId(client.id).get();
            CanvasModel canvas = repoCanva.findCanvasModelByClientId(client.id).get();

            Font titleFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 16, Font.BOLD);

            Paragraph title1 = new Paragraph("Canales", titleFont);
            title1.setAlignment(Element.ALIGN_CENTER);
            document.add(title1);

            Date currentDate = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM yyyy");
            String formattedDate = dateFormat.format(currentDate).toUpperCase();
            document.add(new Paragraph("\n"));
            // Aquí deberías obtener los datos de tu base de datos, si los necesitas
            // Establecer el estilo de fuente para el contenido
            Font contentFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 14, Font.BOLD);
            //Font atributos = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.NORMAL, BaseColor.DARK_GRAY);
            Font contentFont1 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.BOLD);
            Font contentFont2 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.NORMAL);
            Font contentFont3 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.BOLD);
            Font contentFont4 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 11, Font.NORMAL);

            // Datos del formulario
            String informacion = canvas.getChannels().getInformacion();
            String evaluacion = canvas.getChannels().getEvaluacion();
            String compra = canvas.getChannels().getCompra();
            String entrega = canvas.getChannels().getEntrega();
            String postventa = canvas.getChannels().getPostVenta();
            // Crear una tabla en el documento PDF
            PdfPTable table = new PdfPTable(2);
            float[] columnWidths = {25f, 75f}; // Establece las proporciones de ancho

            table.setWidths(columnWidths);

// Agregar celdas a la tabla con los datos del formulario
            PdfPCell cell1 = new PdfPCell(new Phrase("Información", contentFont3));
            PdfPCell cell2 = new PdfPCell(new Phrase(informacion, contentFont4));
            PdfPCell cell3 = new PdfPCell(new Phrase("Evaluación", contentFont3));
            PdfPCell cell4 = new PdfPCell(new Phrase(evaluacion, contentFont4));
            PdfPCell cell5 = new PdfPCell(new Phrase("Compra", contentFont3));
            PdfPCell cell6 = new PdfPCell(new Phrase(compra, contentFont4));
            PdfPCell cell7 = new PdfPCell(new Phrase("Entrega", contentFont3));
            PdfPCell cell8 = new PdfPCell(new Phrase(entrega, contentFont4));
            PdfPCell cell9 = new PdfPCell(new Phrase("Postventa", contentFont3));
            PdfPCell cell0 = new PdfPCell(new Phrase(postventa, contentFont4));

            table.addCell(cell1);
            table.addCell(cell2);
            table.addCell(cell3);
            table.addCell(cell4);
            table.addCell(cell5);
            table.addCell(cell6);
            table.addCell(cell7);
            table.addCell(cell8);
            table.addCell(cell9);
            table.addCell(cell0);

// Agregar la tabla al documento
            document.add(table);

            document.close();

            System.out.println("PDF creado exitosamente.");
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
            // Manejar errores aquí
        }

    }

    @GetMapping("/canvasRelaciones/{id}")//
    public void canvasRelaciones(HttpServletResponse response, @PathVariable Long id) {
        try {
            // Inicializar las imágenes en el constructor
            String imagePathIzq = new File("ImagenCamaraComercioIzquierda.jpg").getAbsolutePath();
            headerImageIzq = Image.getInstance(imagePathIzq);
            headerImageIzq.scaleToFit(150, headerImageIzq.getHeight());

            String imagePathDer = new File("RCP-Auto.jpg").getAbsolutePath();
            headerImageDer = Image.getInstance(imagePathDer);
            headerImageDer.scaleToFit(100, 100);

            String imagePathIzq3 = new File("ImagenCDE_Empresarial.jpg").getAbsolutePath();
            headerImageIzq3 = Image.getInstance(imagePathIzq3);
            headerImageIzq3.scaleToFit(150, 150);
        } catch (IOException | DocumentException e) {
            e.printStackTrace();
        }

        // Establecer el tipo de contenido de la respuesta como PDF
        response.setContentType("application/pdf");
        // Establecer el encabezado para indicar la descarga del archivo PDF
        response.setHeader("Content-Disposition", "attachment; filename=relaciones-con-clientes.pdf");

        try {

            // Crear una instancia del documento y del escritor
            Document document = new Document(PageSize.A4); //
            OutputStream outputStream = response.getOutputStream();
            PdfWriter writer = PdfWriter.getInstance(document, outputStream);
            document.setMargins(document.leftMargin(), document.rightMargin(), 100, 0);

            // Crear una instancia de la clase PdfHeaderEventHandler1 para manejar el encabezado
            PdfHeaderEventHandler4 headerHandler = new PdfHeaderEventHandler4();
            writer.setPageEvent(headerHandler);

            // Abrir el documento para escribir el contenido
            document.open();

            Client client = clientRepository.findById(id).get();
            SelfAssessment selfAssessment = respoSelf.findByClientId(client.id).get();
            CanvasModel canvas = repoCanva.findCanvasModelByClientId(client.id).get();

            Font titleFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 16, Font.BOLD);

            Paragraph title1 = new Paragraph("Relaciones con los Clientes", titleFont);
            title1.setAlignment(Element.ALIGN_CENTER);
            document.add(title1);

            Date currentDate = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM yyyy");
            String formattedDate = dateFormat.format(currentDate).toUpperCase();
            document.add(new Paragraph("\n"));
            // Aquí deberías obtener los datos de tu base de datos, si los necesitas
            // Establecer el estilo de fuente para el contenido
            Font contentFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 14, Font.BOLD);
            //Font atributos = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.NORMAL, BaseColor.DARK_GRAY);
            Font contentFont1 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.BOLD);
            Font contentFont2 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.NORMAL);
            Font contentFont3 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.BOLD);
            Font contentFont4 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 11, Font.NORMAL);

            // Datos del formulario
            String captacion = canvas.getCustomerRelationships().getCaptacion();
            String estimulacion = canvas.getCustomerRelationships().getEstimulacion();
            String fidelizacion = canvas.getCustomerRelationships().getFidelizacion();

            // Crear una tabla en el documento PDF
            PdfPTable table = new PdfPTable(2);
            float[] columnWidths = {25f, 75f}; // Establece las proporciones de ancho

            table.setWidths(columnWidths);

// Agregar celdas a la tabla con los datos del formulario
            PdfPCell cell1 = new PdfPCell(new Phrase("Captación", contentFont3));
            PdfPCell cell2 = new PdfPCell(new Phrase(captacion, contentFont4));
            PdfPCell cell3 = new PdfPCell(new Phrase("Estimulación", contentFont3));
            PdfPCell cell4 = new PdfPCell(new Phrase(estimulacion, contentFont4));
            PdfPCell cell5 = new PdfPCell(new Phrase("Fidelización", contentFont3));
            PdfPCell cell6 = new PdfPCell(new Phrase(fidelizacion, contentFont4));

            table.addCell(cell1);
            table.addCell(cell2);
            table.addCell(cell3);
            table.addCell(cell4);
            table.addCell(cell5);
            table.addCell(cell6);

// Agregar la tabla al documento
            document.add(table);

            document.close();

            System.out.println("PDF creado exitosamente.");
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
            // Manejar errores aquí
        }

    }

    @GetMapping("/canvasRecursos/{id}")//
    public void canvasRecursos(HttpServletResponse response, @PathVariable Long id) {
        try {
            // Inicializar las imágenes en el constructor
            String imagePathIzq = new File("ImagenCamaraComercioIzquierda.jpg").getAbsolutePath();
            headerImageIzq = Image.getInstance(imagePathIzq);
            headerImageIzq.scaleToFit(150, headerImageIzq.getHeight());

            String imagePathDer = new File("RCP-Auto.jpg").getAbsolutePath();
            headerImageDer = Image.getInstance(imagePathDer);
            headerImageDer.scaleToFit(100, 100);

            String imagePathIzq3 = new File("ImagenCDE_Empresarial.jpg").getAbsolutePath();
            headerImageIzq3 = Image.getInstance(imagePathIzq3);
            headerImageIzq3.scaleToFit(150, 150);
        } catch (IOException | DocumentException e) {
            e.printStackTrace();
        }

        // Establecer el tipo de contenido de la respuesta como PDF
        response.setContentType("application/pdf");
        // Establecer el encabezado para indicar la descarga del archivo PDF
        response.setHeader("Content-Disposition", "attachment; filename=recursos-claves.pdf");

        try {

            // Crear una instancia del documento y del escritor
            Document document = new Document(PageSize.A4); //
            OutputStream outputStream = response.getOutputStream();
            PdfWriter writer = PdfWriter.getInstance(document, outputStream);
            document.setMargins(document.leftMargin(), document.rightMargin(), 100, 0);

            // Crear una instancia de la clase PdfHeaderEventHandler1 para manejar el encabezado
            PdfHeaderEventHandler4 headerHandler = new PdfHeaderEventHandler4();
            writer.setPageEvent(headerHandler);

            // Abrir el documento para escribir el contenido
            document.open();

            Client client = clientRepository.findById(id).get();
            SelfAssessment selfAssessment = respoSelf.findByClientId(client.id).get();
            CanvasModel canvas = repoCanva.findCanvasModelByClientId(client.id).get();

            Font titleFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 16, Font.BOLD);

            Paragraph title1 = new Paragraph("Recursos Claves", titleFont);
            title1.setAlignment(Element.ALIGN_CENTER);
            document.add(title1);

            Date currentDate = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM yyyy");
            String formattedDate = dateFormat.format(currentDate).toUpperCase();
            document.add(new Paragraph("\n"));
            // Aquí deberías obtener los datos de tu base de datos, si los necesitas
            // Establecer el estilo de fuente para el contenido
            Font contentFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 14, Font.BOLD);
            //Font atributos = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.NORMAL, BaseColor.DARK_GRAY);
            Font contentFont1 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.BOLD);
            Font contentFont2 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.NORMAL);
            Font contentFont3 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.BOLD);
            Font contentFont4 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 11, Font.NORMAL);

            // Datos del formulario
            String humanos = canvas.getKeyRecources().getRecursosHumanos();
            String fisicos = canvas.getKeyRecources().getRecursosFisicos();
            String intelectuales = canvas.getKeyRecources().getRecursosIntelectuales();
            String tecnologicos = canvas.getKeyRecources().getRecursosTecnologicos();
            String otros = canvas.getKeyRecources().getOtros();
            // Crear una tabla en el documento PDF
            PdfPTable table = new PdfPTable(2);
            float[] columnWidths = {25f, 75f}; // Establece las proporciones de ancho

            table.setWidths(columnWidths);

// Agregar celdas a la tabla con los datos del formulario
            PdfPCell cell1 = new PdfPCell(new Phrase("Recursos Humanos", contentFont3));
            PdfPCell cell2 = new PdfPCell(new Phrase(humanos, contentFont4));
            PdfPCell cell3 = new PdfPCell(new Phrase("Recursos Físicos:", contentFont3));
            PdfPCell cell4 = new PdfPCell(new Phrase(fisicos, contentFont4));
            PdfPCell cell5 = new PdfPCell(new Phrase("Recursos Intelectuales", contentFont3));
            PdfPCell cell6 = new PdfPCell(new Phrase(intelectuales, contentFont4));
            PdfPCell cell7 = new PdfPCell(new Phrase("Recursos Tecnológicos", contentFont3));
            PdfPCell cell8 = new PdfPCell(new Phrase(tecnologicos, contentFont4));
            PdfPCell cell9 = new PdfPCell(new Phrase("Otros", contentFont3));
            PdfPCell cell0 = new PdfPCell(new Phrase(otros, contentFont4));

            table.addCell(cell1);
            table.addCell(cell2);
            table.addCell(cell3);
            table.addCell(cell4);
            table.addCell(cell5);
            table.addCell(cell6);
            table.addCell(cell7);
            table.addCell(cell8);
            table.addCell(cell9);
            table.addCell(cell0);

// Agregar la tabla al documento
            document.add(table);

            document.close();

            System.out.println("PDF creado exitosamente.");
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
            // Manejar errores aquí
        }

    }

    @GetMapping("/canvasActividades/{id}")//
    public void canvasActividades(HttpServletResponse response, @PathVariable Long id) {
        try {
            // Inicializar las imágenes en el constructor
            String imagePathIzq = new File("ImagenCamaraComercioIzquierda.jpg").getAbsolutePath();
            headerImageIzq = Image.getInstance(imagePathIzq);
            headerImageIzq.scaleToFit(150, headerImageIzq.getHeight());

            String imagePathDer = new File("RCP-Auto.jpg").getAbsolutePath();
            headerImageDer = Image.getInstance(imagePathDer);
            headerImageDer.scaleToFit(100, 100);

            String imagePathIzq3 = new File("ImagenCDE_Empresarial.jpg").getAbsolutePath();
            headerImageIzq3 = Image.getInstance(imagePathIzq3);
            headerImageIzq3.scaleToFit(150, 150);
        } catch (IOException | DocumentException e) {
            e.printStackTrace();
        }

        // Establecer el tipo de contenido de la respuesta como PDF
        response.setContentType("application/pdf");
        // Establecer el encabezado para indicar la descarga del archivo PDF
        response.setHeader("Content-Disposition", "attachment; filename=actividades-claves.pdf");

        try {

            // Crear una instancia del documento y del escritor
            Document document = new Document(PageSize.A4); //
            OutputStream outputStream = response.getOutputStream();
            PdfWriter writer = PdfWriter.getInstance(document, outputStream);
            document.setMargins(document.leftMargin(), document.rightMargin(), 100, 0);

            // Crear una instancia de la clase PdfHeaderEventHandler1 para manejar el encabezado
            PdfHeaderEventHandler4 headerHandler = new PdfHeaderEventHandler4();
            writer.setPageEvent(headerHandler);

            // Abrir el documento para escribir el contenido
            document.open();

            Client client = clientRepository.findById(id).get();
            SelfAssessment selfAssessment = respoSelf.findByClientId(client.id).get();
            CanvasModel canvas = repoCanva.findCanvasModelByClientId(client.id).get();

            Font titleFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 16, Font.BOLD);

            Paragraph title1 = new Paragraph("Actividades Claves", titleFont);
            title1.setAlignment(Element.ALIGN_CENTER);
            document.add(title1);

            Date currentDate = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM yyyy");
            String formattedDate = dateFormat.format(currentDate).toUpperCase();
            document.add(new Paragraph("\n"));
            // Aquí deberías obtener los datos de tu base de datos, si los necesitas
            // Establecer el estilo de fuente para el contenido
            Font contentFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 14, Font.BOLD);
            //Font atributos = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.NORMAL, BaseColor.DARK_GRAY);
            Font contentFont1 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.BOLD);
            Font contentFont2 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.NORMAL);
            Font contentFont3 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.BOLD);
            Font contentFont4 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 11, Font.NORMAL);

            // Datos del formulario
            String pricipal = canvas.getKeyActivities().getActividadPrincipal();
            String servicio = canvas.getKeyActivities().getPrestacionServicio();
            String marketing = canvas.getKeyActivities().getComunicacionMarketing();
            String posVenta = canvas.getKeyActivities().getPostVenta();
            String otros = canvas.getKeyActivities().getOtros();
            // Crear una tabla en el documento PDF
            PdfPTable table = new PdfPTable(2);
            float[] columnWidths = {25f, 75f}; // Establece las proporciones de ancho

            table.setWidths(columnWidths);

// Agregar celdas a la tabla con los datos del formulario
            PdfPCell cell1 = new PdfPCell(new Phrase("Actividad Principal", contentFont3));
            PdfPCell cell2 = new PdfPCell(new Phrase(pricipal, contentFont4));
            PdfPCell cell3 = new PdfPCell(new Phrase("Prestación del Servicio", contentFont3));
            PdfPCell cell4 = new PdfPCell(new Phrase(servicio, contentFont4));
            PdfPCell cell5 = new PdfPCell(new Phrase("Comunicación y Marketing", contentFont3));
            PdfPCell cell6 = new PdfPCell(new Phrase(marketing, contentFont4));
            PdfPCell cell7 = new PdfPCell(new Phrase("PostVenta", contentFont3));
            PdfPCell cell8 = new PdfPCell(new Phrase(posVenta, contentFont4));
            PdfPCell cell9 = new PdfPCell(new Phrase("Otros", contentFont3));
            PdfPCell cell0 = new PdfPCell(new Phrase(otros, contentFont4));

            table.addCell(cell1);
            table.addCell(cell2);
            table.addCell(cell3);
            table.addCell(cell4);
            table.addCell(cell5);
            table.addCell(cell6);
            table.addCell(cell7);
            table.addCell(cell8);
            table.addCell(cell9);
            table.addCell(cell0);

// Agregar la tabla al documento
            document.add(table);

            document.close();

            System.out.println("PDF creado exitosamente.");
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
            // Manejar errores aquí
        }

    }

    @GetMapping("/canvasSocios/{id}")//
    public void canvasSocios(HttpServletResponse response, @PathVariable Long id) {
        try {
            // Inicializar las imágenes en el constructor
            String imagePathIzq = new File("ImagenCamaraComercioIzquierda.jpg").getAbsolutePath();
            headerImageIzq = Image.getInstance(imagePathIzq);
            headerImageIzq.scaleToFit(150, headerImageIzq.getHeight());

            String imagePathDer = new File("RCP-Auto.jpg").getAbsolutePath();
            headerImageDer = Image.getInstance(imagePathDer);
            headerImageDer.scaleToFit(100, 100);

            String imagePathIzq3 = new File("ImagenCDE_Empresarial.jpg").getAbsolutePath();
            headerImageIzq3 = Image.getInstance(imagePathIzq3);
            headerImageIzq3.scaleToFit(150, 150);
        } catch (IOException | DocumentException e) {
            e.printStackTrace();
        }

        // Establecer el tipo de contenido de la respuesta como PDF
        response.setContentType("application/pdf");
        // Establecer el encabezado para indicar la descarga del archivo PDF
        response.setHeader("Content-Disposition", "attachment; filename=socios-claves.pdf");

        try {

            // Crear una instancia del documento y del escritor
            Document document = new Document(PageSize.A4); //
            OutputStream outputStream = response.getOutputStream();
            PdfWriter writer = PdfWriter.getInstance(document, outputStream);
            document.setMargins(document.leftMargin(), document.rightMargin(), 100, 0);

            // Crear una instancia de la clase PdfHeaderEventHandler1 para manejar el encabezado
            PdfHeaderEventHandler4 headerHandler = new PdfHeaderEventHandler4();
            writer.setPageEvent(headerHandler);

            // Abrir el documento para escribir el contenido
            document.open();

            Client client = clientRepository.findById(id).get();
            SelfAssessment selfAssessment = respoSelf.findByClientId(client.id).get();
            CanvasModel canvas = repoCanva.findCanvasModelByClientId(client.id).get();

            Font titleFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 16, Font.BOLD);

            Paragraph title1 = new Paragraph("Socios Claves", titleFont);
            title1.setAlignment(Element.ALIGN_CENTER);
            document.add(title1);

            Date currentDate = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM yyyy");
            String formattedDate = dateFormat.format(currentDate).toUpperCase();
            document.add(new Paragraph("\n"));
            // Aquí deberías obtener los datos de tu base de datos, si los necesitas
            // Establecer el estilo de fuente para el contenido
            Font contentFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 14, Font.BOLD);
            //Font atributos = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.NORMAL, BaseColor.DARK_GRAY);
            Font contentFont1 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.BOLD);
            Font contentFont2 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.NORMAL);
            Font contentFont3 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.BOLD);
            Font contentFont4 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 11, Font.NORMAL);

            // Datos del formulario
            String proveedores = canvas.getKeyPartners().getProveedores();
            String publicas = canvas.getKeyPartners().getEntidadesPublicas();
            String privadas = canvas.getKeyPartners().getEntidadesPrivadas();
            String academia = canvas.getKeyPartners().getAcademia();
            String otros = canvas.getKeyPartners().getOtros();
            // Crear una tabla en el documento PDF
            PdfPTable table = new PdfPTable(2);
            float[] columnWidths = {25f, 75f}; // Establece las proporciones de ancho

            table.setWidths(columnWidths);

// Agregar celdas a la tabla con los datos del formulario
            PdfPCell cell1 = new PdfPCell(new Phrase("Proveedores", contentFont3));
            PdfPCell cell2 = new PdfPCell(new Phrase(proveedores, contentFont4));
            PdfPCell cell3 = new PdfPCell(new Phrase("Entidades Públicas", contentFont3));
            PdfPCell cell4 = new PdfPCell(new Phrase(publicas, contentFont4));
            PdfPCell cell5 = new PdfPCell(new Phrase("Entidades Privadas", contentFont3));
            PdfPCell cell6 = new PdfPCell(new Phrase(privadas, contentFont4));
            PdfPCell cell7 = new PdfPCell(new Phrase("Academia", contentFont3));
            PdfPCell cell8 = new PdfPCell(new Phrase(academia, contentFont4));
            PdfPCell cell9 = new PdfPCell(new Phrase("Otros", contentFont3));
            PdfPCell cell0 = new PdfPCell(new Phrase(otros, contentFont4));

            table.addCell(cell1);
            table.addCell(cell2);
            table.addCell(cell3);
            table.addCell(cell4);
            table.addCell(cell5);
            table.addCell(cell6);
            table.addCell(cell7);
            table.addCell(cell8);
            table.addCell(cell9);
            table.addCell(cell0);

// Agregar la tabla al documento
            document.add(table);

            document.close();

            System.out.println("PDF creado exitosamente.");
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
            // Manejar errores aquí
        }

    }

    @GetMapping("/canvasIngresos/{id}")//
    public void canvasIngresos(HttpServletResponse response, @PathVariable Long id) {
        try {
            // Inicializar las imágenes en el constructor
            String imagePathIzq = new File("ImagenCamaraComercioIzquierda.jpg").getAbsolutePath();
            headerImageIzq = Image.getInstance(imagePathIzq);
            headerImageIzq.scaleToFit(150, headerImageIzq.getHeight());

            String imagePathDer = new File("RCP-Auto.jpg").getAbsolutePath();
            headerImageDer = Image.getInstance(imagePathDer);
            headerImageDer.scaleToFit(100, 100);

            String imagePathIzq3 = new File("ImagenCDE_Empresarial.jpg").getAbsolutePath();
            headerImageIzq3 = Image.getInstance(imagePathIzq3);
            headerImageIzq3.scaleToFit(150, 150);
        } catch (IOException | DocumentException e) {
            e.printStackTrace();
        }

        // Establecer el tipo de contenido de la respuesta como PDF
        response.setContentType("application/pdf");
        // Establecer el encabezado para indicar la descarga del archivo PDF
        response.setHeader("Content-Disposition", "attachment; filename=ingresos.pdf");

        try {

            // Crear una instancia del documento y del escritor
            Document document = new Document(PageSize.A4); //
            OutputStream outputStream = response.getOutputStream();
            PdfWriter writer = PdfWriter.getInstance(document, outputStream);
            document.setMargins(document.leftMargin(), document.rightMargin(), 100, 0);

            // Crear una instancia de la clase PdfHeaderEventHandler1 para manejar el encabezado
            PdfHeaderEventHandler4 headerHandler = new PdfHeaderEventHandler4();
            writer.setPageEvent(headerHandler);

            // Abrir el documento para escribir el contenido
            document.open();

            Client client = clientRepository.findById(id).get();
            SelfAssessment selfAssessment = respoSelf.findByClientId(client.id).get();
            CanvasModel canvas = repoCanva.findCanvasModelByClientId(client.id).get();

            Font titleFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 16, Font.BOLD);

            Paragraph title1 = new Paragraph("Ingresos", titleFont);
            title1.setAlignment(Element.ALIGN_CENTER);
            document.add(title1);

            Date currentDate = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM yyyy");
            String formattedDate = dateFormat.format(currentDate).toUpperCase();
            document.add(new Paragraph("\n"));
            // Aquí deberías obtener los datos de tu base de datos, si los necesitas
            // Establecer el estilo de fuente para el contenido
            Font contentFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 14, Font.BOLD);
            //Font atributos = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.NORMAL, BaseColor.DARK_GRAY);
            Font contentFont1 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.BOLD);
            Font contentFont2 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.NORMAL);
            Font contentFont3 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.BOLD);
            Font contentFont4 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 11, Font.NORMAL);

            // Datos del formulario
            String propio = canvas.getRevenueStreams().getCapitalPorpio();
            String prestamo = canvas.getRevenueStreams().getCapitalPrestamo();
            String pago = canvas.getRevenueStreams().getCanalesPago();
            String otros = canvas.getRevenueStreams().getOtros();
            // Crear una tabla en el documento PDF
            PdfPTable table = new PdfPTable(2);
            float[] columnWidths = {25f, 75f}; // Establece las proporciones de ancho

            table.setWidths(columnWidths);

// Agregar celdas a la tabla con los datos del formulario
            PdfPCell cell1 = new PdfPCell(new Phrase("Capital Propio", contentFont3));
            PdfPCell cell2 = new PdfPCell(new Phrase(propio, contentFont4));
            PdfPCell cell3 = new PdfPCell(new Phrase("Capital Préstamo", contentFont3));
            PdfPCell cell4 = new PdfPCell(new Phrase(prestamo, contentFont4));
            PdfPCell cell5 = new PdfPCell(new Phrase("Canales de Pago", contentFont3));
            PdfPCell cell6 = new PdfPCell(new Phrase(pago, contentFont4));
            PdfPCell cell9 = new PdfPCell(new Phrase("Otros", contentFont3));
            PdfPCell cell0 = new PdfPCell(new Phrase(otros, contentFont4));

            table.addCell(cell1);
            table.addCell(cell2);
            table.addCell(cell3);
            table.addCell(cell4);
            table.addCell(cell5);
            table.addCell(cell6);
            table.addCell(cell9);
            table.addCell(cell0);

// Agregar la tabla al documento
            document.add(table);

            document.close();

            System.out.println("PDF creado exitosamente.");
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
            // Manejar errores aquí
        }

    }

    @GetMapping("/canvasCostos/{id}")//
    public void canvasCostos(HttpServletResponse response, @PathVariable Long id) {
        try {
            // Inicializar las imágenes en el constructor
            String imagePathIzq = new File("ImagenCamaraComercioIzquierda.jpg").getAbsolutePath();
            headerImageIzq = Image.getInstance(imagePathIzq);
            headerImageIzq.scaleToFit(150, headerImageIzq.getHeight());

            String imagePathDer = new File("RCP-Auto.jpg").getAbsolutePath();
            headerImageDer = Image.getInstance(imagePathDer);
            headerImageDer.scaleToFit(100, 100);

            String imagePathIzq3 = new File("ImagenCDE_Empresarial.jpg").getAbsolutePath();
            headerImageIzq3 = Image.getInstance(imagePathIzq3);
            headerImageIzq3.scaleToFit(150, 150);
        } catch (IOException | DocumentException e) {
            e.printStackTrace();
        }

        // Establecer el tipo de contenido de la respuesta como PDF
        response.setContentType("application/pdf");
        // Establecer el encabezado para indicar la descarga del archivo PDF
        response.setHeader("Content-Disposition", "attachment; filename=estructura-costos.pdf");

        try {

            // Crear una instancia del documento y del escritor
            Document document = new Document(PageSize.A4); //
            OutputStream outputStream = response.getOutputStream();
            PdfWriter writer = PdfWriter.getInstance(document, outputStream);
            document.setMargins(document.leftMargin(), document.rightMargin(), 100, 0);

            // Crear una instancia de la clase PdfHeaderEventHandler1 para manejar el encabezado
            PdfHeaderEventHandler4 headerHandler = new PdfHeaderEventHandler4();
            writer.setPageEvent(headerHandler);

            // Abrir el documento para escribir el contenido
            document.open();

            Client client = clientRepository.findById(id).get();
            SelfAssessment selfAssessment = respoSelf.findByClientId(client.id).get();
            CanvasModel canvas = repoCanva.findCanvasModelByClientId(client.id).get();

            Font titleFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 16, Font.BOLD);

            Paragraph title1 = new Paragraph("Estructura de Costos", titleFont);
            title1.setAlignment(Element.ALIGN_CENTER);
            document.add(title1);

            Date currentDate = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM yyyy");
            String formattedDate = dateFormat.format(currentDate).toUpperCase();
            document.add(new Paragraph("\n"));
            // Aquí deberías obtener los datos de tu base de datos, si los necesitas
            // Establecer el estilo de fuente para el contenido
            Font contentFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 14, Font.BOLD);
            //Font atributos = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.NORMAL, BaseColor.DARK_GRAY);
            Font contentFont1 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.BOLD);
            Font contentFont2 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.NORMAL);
            Font contentFont3 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.BOLD);
            Font contentFont4 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 11, Font.NORMAL);

            // Crear una tabla en el documento PDF
            String costoVariableTitle = "Costos Variables";
            String costoFijoTitle = "Costos Fijos";
            List<CostComponent> costosFijos = canvas.getCostStructure().getCostosFijos();
            List<CostComponent> costosVariables = canvas.getCostStructure().getCostosVariables();

            // Crear una tabla para el contenido de Costo Variable
            PdfPTable table1 = new PdfPTable(2);
            PdfPCell cell11 = new PdfPCell(new Paragraph(costoVariableTitle));
            cell11.setColspan(2);
            table1.addCell(cell11);

            // Agregar filas con contenido de Costo Variable
            for (CostComponent variables : costosVariables) {
                table1.addCell(variables.getNameComponent() + " - $ " + variables.getAmount());
                // Agregar la celda a la tabla, creará una nueva fila en cada iteración
            }

            // Crear una tabla para el contenido de Costo Fijo
            PdfPTable table2 = new PdfPTable(2);
            PdfPCell cell21 = new PdfPCell(new Paragraph(costoFijoTitle));
            cell21.setColspan(2);
            table2.addCell(cell21);

            // Agregar filas con contenido de Costo Fijo
            for (CostComponent fijos : costosFijos) {
                table2.addCell(fijos.getNameComponent() + " - $ " + fijos.getAmount());
                // Agregar la celda a la tabla, creará una nueva fila en cada iteración
            }

            // Agregar las tablas al documento PDF
            document.add(table1);
            document.add(table2);

            document.close();

            System.out.println("PDF creado exitosamente.");
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
            // Manejar errores aquí
        }

    }

    private static class PdfFooterEventHandler extends PdfPageEventHelper {

        @Override
        public void onEndPage(PdfWriter writer, Document document) {
            try {
                // Obtener el número de página actual
                int pageNumber = writer.getPageNumber();

                // Obtener el contenido directo de la página
                PdfContentByte content = writer.getDirectContent();

                // Agregar imagen en el pie de página
                String imagePath = new File("PiePagina.jpg").getAbsolutePath();
                Image footerImage = Image.getInstance(imagePath);
                footerImage.scaleToFit(PageSize.A4.getWidth(), footerImage.getHeight());
                footerImage.setAbsolutePosition(0, 0);
                content.addImage(footerImage);
            } catch (IOException | DocumentException e) {
                e.printStackTrace();
            }
        }
    }

    private static class PdfHeaderEventHandler extends PdfPageEventHelper {

        private PdfHeaderEventHandler() {
        }

        @Override
        public void onStartPage(PdfWriter writer, Document document) {
            try {
                // Obtener el número de página actual
                int pageNumber = writer.getPageNumber();

                // Obtener el contenido directo de la página
                PdfContentByte content = writer.getDirectContent();

                // Agregar imagen en el encabezado
                String imagePathIzq = new File("ImagenEncabezado1.jpg").getAbsolutePath();
                Image headerImageIzq = Image.getInstance(imagePathIzq);
                headerImageIzq.scaleToFit(200, headerImageIzq.getHeight());
                float imageX1 = 36;
                float imageY1 = PageSize.A4.getHeight() - 20 - headerImageIzq.getScaledHeight();
                headerImageIzq.setAbsolutePosition(imageX1, imageY1);
                content.addImage(headerImageIzq);

                String imagePathDer = new File("ImagenEncabezado2.jpg").getAbsolutePath();
                Image headerImageDer = Image.getInstance(imagePathDer);
                headerImageDer.scaleToFit(100, 100);
                float imageX2 = PageSize.A4.getWidth() - 136;
                float imageY2 = PageSize.A4.getHeight() - 20 - headerImageDer.getScaledHeight();
                headerImageDer.setAbsolutePosition(imageX2, imageY2);
                content.addImage(headerImageDer);
            } catch (IOException | DocumentException e) {
                e.printStackTrace();
            }
        }
    }

//para cada uno las imagenes del encabezado
    private static class PdfFooterEventHandler1 extends PdfPageEventHelper {

        @Override
        public void onEndPage(PdfWriter writer, Document document) {
            try {
                // Obtener el número de página actual
                int pageNumber = writer.getPageNumber();

                // Obtener el contenido directo de la página
                PdfContentByte content = writer.getDirectContent();

                // Agregar imagen en el pie de página
                String imagePath = new File("PiePagina.jpg").getAbsolutePath();
                Image footerImage = Image.getInstance(imagePath);
                footerImage.scaleToFit(PageSize.A4.getWidth(), footerImage.getHeight());
                footerImage.setAbsolutePosition(0, 0);
                content.addImage(footerImage);
            } catch (IOException | DocumentException e) {
                e.printStackTrace();
            }
        }
    }

    private static class PdfHeaderEventHandler1 extends PdfPageEventHelper {

        @Override
        public void onStartPage(PdfWriter writer, Document document) {
            try {
                // Obtener el número de página actual
                int pageNumber = writer.getPageNumber();

                // Obtener el contenido directo de la página
                PdfContentByte content = writer.getDirectContent();

                // Agregar imagen en el encabezado
                String imagePathIzq = new File("ImagenCamaraComercioIzquierda.jpg").getAbsolutePath();
                Image headerImageIzq = Image.getInstance(imagePathIzq);
                headerImageIzq.scaleToFit(150, headerImageIzq.getHeight());
                float imageX1 = 36;
                float imageY1 = PageSize.A4.getHeight() - 20 - headerImageIzq.getScaledHeight();
                headerImageIzq.setAbsolutePosition(imageX1, imageY1);
                content.addImage(headerImageIzq);

                String imagePathDer = new File("CodigoRCP-HerramientaDiagnostico.jpg").getAbsolutePath();
                Image headerImageDer = Image.getInstance(imagePathDer);
                headerImageDer.scaleToFit(100, 100);
                float imageX2 = PageSize.A4.getWidth() - 136;
                float imageY2 = PageSize.A4.getHeight() - 20 - headerImageDer.getScaledHeight();
                headerImageDer.setAbsolutePosition(imageX2, imageY2);
                content.addImage(headerImageDer);

                //Imagen Derecha pero a la izquierda de la imagen del metodo de abajo
                String imagePathIzq3 = new File("ImagenCDE_Empresarial.jpg").getAbsolutePath();
                Image headerImageIzq3 = Image.getInstance(imagePathIzq3);
                headerImageIzq3.scaleToFit(150, 150); // Ajusta el tamaño de la tercera imagen según tus necesidades
                float imageX3 = imageX2 - headerImageIzq3.getScaledWidth() - 10; // Coloca la tercera imagen a la izquierda de la segunda imagen
                float imageY3 = imageY2 - 10; // Mantiene la misma altura que la segunda imagen
                headerImageIzq3.setAbsolutePosition(imageX3, imageY3);
                content.addImage(headerImageIzq3);

            } catch (IOException | DocumentException e) {
                e.printStackTrace();
            }
        }
    }

    private static Image headerImageIzq;
    private static Image headerImageDer;
    private static Image headerImageIzq3;

    private static class PdfHeaderEventHandler2 extends PdfPageEventHelper {

        @Override
        public void onStartPage(PdfWriter writer, Document document) {
            try {
                // Obtener el número de página actual
                int pageNumber = writer.getPageNumber();

                // Obtener el contenido directo de la página
                PdfContentByte content = writer.getDirectContent();

                // Definir las coordenadas y escalado de las imágenes
                float imageX1 = 36;
                float imageY1 = PageSize.A4.getHeight() - 20 - headerImageIzq.getScaledHeight();
                float imageX2 = PageSize.A4.getWidth() - 136;
                float imageY2 = PageSize.A4.getHeight() - 20 - headerImageDer.getScaledHeight();
                float imageX3 = imageX2 - headerImageIzq3.getScaledWidth() - 10;
                float imageY3 = imageY2 - 10;

                // Agregar imagen en el encabezado
                String imagePathIzq = new File("ImagenCamaraComercioIzquierda.jpg").getAbsolutePath();
                Image headerImageIzq = Image.getInstance(imagePathIzq);
                headerImageIzq.scaleToFit(150, headerImageIzq.getHeight());
                headerImageIzq.setAbsolutePosition(imageX1, imageY1);
                content.addImage(headerImageIzq);

                String imagePathDer = new File("CodigoRCP-HerramientaDiagnostico.jpg").getAbsolutePath();
                Image headerImageDer = Image.getInstance(imagePathDer);
                headerImageDer.scaleToFit(100, 100);
                headerImageDer.setAbsolutePosition(imageX2, imageY2);
                content.addImage(headerImageDer);

                String imagePathIzq3 = new File("ImagenCDE_Empresarial.jpg").getAbsolutePath();
                Image headerImageIzq3 = Image.getInstance(imagePathIzq3);
                headerImageIzq3.scaleToFit(150, 150);
                headerImageIzq3.setAbsolutePosition(imageX3, imageY3);
                content.addImage(headerImageIzq3);

            } catch (IOException | DocumentException e) {
                e.printStackTrace();
            }
        }
    }

    private static class PdfHeaderEventHandler3 extends PdfPageEventHelper {

        @Override
        public void onStartPage(PdfWriter writer, Document document) {
            try {
                // Obtener el número de página actual
                int pageNumber = writer.getPageNumber();

                // Obtener el contenido directo de la página
                PdfContentByte content = writer.getDirectContent();

                // Definir las coordenadas y escalado de las imágenes
                float imageX1 = 36;
                float imageY1 = PageSize.A4.getHeight() - 20 - headerImageIzq.getScaledHeight();
                float imageX2 = PageSize.A4.getWidth() - 136;
                float imageY2 = PageSize.A4.getHeight() - 20 - headerImageDer.getScaledHeight();
                float imageX3 = imageX2 - headerImageIzq3.getScaledWidth() - 10;
                float imageY3 = imageY2 - 10;

                // Agregar imagen en el encabezado
                String imagePathIzq = new File("ImagenCamaraComercioIzquierda.jpg").getAbsolutePath();
                Image headerImageIzq = Image.getInstance(imagePathIzq);
                headerImageIzq.scaleToFit(150, headerImageIzq.getHeight());
                headerImageIzq.setAbsolutePosition(imageX1, imageY1);
                content.addImage(headerImageIzq);

                String imagePathDer = new File("RCP-Auto.jpg").getAbsolutePath();
                Image headerImageDer = Image.getInstance(imagePathDer);
                headerImageDer.scaleToFit(100, 100);
                headerImageDer.setAbsolutePosition(imageX2, imageY2);
                content.addImage(headerImageDer);

                String imagePathIzq3 = new File("ImagenCDE_Empresarial.jpg").getAbsolutePath();
                Image headerImageIzq3 = Image.getInstance(imagePathIzq3);
                headerImageIzq3.scaleToFit(150, 150);
                headerImageIzq3.setAbsolutePosition(imageX3, imageY3);
                content.addImage(headerImageIzq3);

            } catch (IOException | DocumentException e) {
                e.printStackTrace();
            }
        }
    }

    private static class PdfHeaderEventHandler4 extends PdfPageEventHelper {

        @Override
        public void onStartPage(PdfWriter writer, Document document) {
            try {
                // Obtener el número de página actual
                int pageNumber = writer.getPageNumber();

                // Obtener el contenido directo de la página
                PdfContentByte content = writer.getDirectContent();

                // Definir las coordenadas y escalado de las imágenes
                float imageX1 = 36;
                float imageY1 = PageSize.A4.getHeight() - 20 - headerImageIzq.getScaledHeight();
                float imageX2 = PageSize.A4.getWidth() - 136;
                float imageY2 = PageSize.A4.getHeight() - 20 - headerImageDer.getScaledHeight();
                float imageX3 = imageX2 - headerImageIzq3.getScaledWidth() - 10;
                float imageY3 = imageY2 - 15;

                // Agregar imagen en el encabezado
                String imagePathIzq = new File("ImagenCamaraComercioIzquierda.jpg").getAbsolutePath();
                Image headerImageIzq = Image.getInstance(imagePathIzq);
                headerImageIzq.scaleToFit(150, headerImageIzq.getHeight());
                headerImageIzq.setAbsolutePosition(imageX1, imageY1);
                content.addImage(headerImageIzq);

                String imagePathDer = new File("enBlanco.jpg").getAbsolutePath();
                Image headerImageDer = Image.getInstance(imagePathDer);
                headerImageDer.scaleToFit(100, 100);
                headerImageDer.setAbsolutePosition(imageX2, imageY2);
                content.addImage(headerImageDer);

                String imagePathIzq3 = new File("ImagenCDE_Empresarial.jpg").getAbsolutePath();
                Image headerImageIzq3 = Image.getInstance(imagePathIzq3);
                headerImageIzq3.scaleToFit(150, 150);
                headerImageIzq3.setAbsolutePosition(imageX3, imageY3);
                content.addImage(headerImageIzq3);

            } catch (IOException | DocumentException e) {
                e.printStackTrace();
            }
        }
    }

}
