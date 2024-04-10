/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.posta.crm.controller;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.posta.crm.cambios.entity.Proceso;
import com.posta.crm.cambios.service.ProcesoService;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.beans.factory.annotation.Autowired;
import com.posta.crm.entity.Process;
import java.io.File;
import java.io.FileOutputStream;
import java.net.MalformedURLException;
import java.time.Instant;
import java.util.NoSuchElementException;
import java.util.UUID;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

@CrossOrigin(origins = "*")
@RequestMapping("/image")
@RestController
public class ImageController {

    private static final String UPLOAD_DIR = "img/";
    //private static final String UPLOAD_DIR = System.getenv("UPLOAD_DIR");
    //private static final String BUCKET_NAME = "posta-app";
    @Autowired
    private ProcesoService processServiceImpl;

    //Metodos Nuevos para la carga de imagenes/archivos
    @PostMapping("/uploadCompromiso/{id}")
    public String uploadFile(@RequestParam("file") MultipartFile file, @PathVariable String id) {
        try {
            // Verifica si el archivo está vacío
            if (file.isEmpty()) {
                return "Archivo vacío";
            }
            
            // Crea el directorio de carga si no existe
            File uploadDir = new File(UPLOAD_DIR);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            // Obtiene el nombre del archivo original
            String fileName = file.getOriginalFilename();

            // Guarda el archivo en el directorio de carga
            File destFile = new File(UPLOAD_DIR + fileName);
            FileOutputStream fos = new FileOutputStream(destFile);
            fos.write(file.getBytes());
            fos.close();

            // Obtiene la entidad desde la base de datos
            Proceso proceso = processServiceImpl.findById(id).get();
            if (proceso == null) {
                return "La entidad no existe";
            }

            // Asigna la ruta del documento compromiso a la entidad
            proceso.setDocumentoCompromiso(fileName); // Asigna el nombre del archivo como ruta del documento compromiso
            processServiceImpl.save(proceso);

            return "Archivo cargado exitosamente y asociado a la entidad: " + fileName;
        } catch (IOException e) {
            return "Error al cargar el archivo";
        }
    }
    
     @GetMapping("/downloadCompromiso/{id}")
    public ResponseEntity<?> downloadFile(@PathVariable String id) throws MalformedURLException {
        Proceso proceso = processServiceImpl.findById(id).orElse(null);
        if (proceso == null || proceso.getDocumentoCompromiso() == null) {
            return ResponseEntity.notFound().build();
        }

        String fileName = proceso.getDocumentoCompromiso();
        Path filePath = Paths.get(UPLOAD_DIR + fileName);
        Resource resource;
        resource = new UrlResource(filePath.toUri());

        // Configura la respuesta para descargar el archivo
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName)
                .body(resource);
    }

    //Metodos Nuevos para la carga de imagenes/archivos
    @PostMapping("/uploadEncuesta/{id}")
    public String uploadSatisfaccion(@RequestParam("file") MultipartFile file, @PathVariable String id) {
        try {
            // Verifica si el archivo está vacío
            if (file.isEmpty()) {
                return "Archivo vacío";
            }
            
            // Crea el directorio de carga si no existe
            File uploadDir = new File(UPLOAD_DIR);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            // Obtiene el nombre del archivo original
            String fileName = file.getOriginalFilename();

            // Guarda el archivo en el directorio de carga
            File destFile = new File(UPLOAD_DIR + fileName);
            FileOutputStream fos = new FileOutputStream(destFile);
            fos.write(file.getBytes());
            fos.close();

            // Obtiene la entidad desde la base de datos
            Proceso proceso = processServiceImpl.findById(id).get();
            if (proceso == null) {
                return "La entidad no existe";
            }

            // Asigna la ruta del documento compromiso a la entidad
            proceso.setEncuestaSatisfaccion(fileName); // Asigna el nombre del archivo como ruta del documento compromiso
            processServiceImpl.save(proceso);

            return "Archivo cargado exitosamente y asociado a la entidad: " + fileName;
        } catch (IOException e) {
            return "Error al cargar el archivo";
        }
    }
    
     @GetMapping("/imagenEncuesta/{id}")
    public ResponseEntity<?> downloadSatisfaccion(@PathVariable String id) throws MalformedURLException {
        Proceso proceso = processServiceImpl.findById(id).orElse(null);
        if (proceso == null || proceso.getDocumentoCompromiso() == null) {
            return ResponseEntity.notFound().build();
        }

        String fileName = proceso.getEncuestaSatisfaccion();
        Path filePath = Paths.get(UPLOAD_DIR + fileName);
        Resource resource;
        resource = new UrlResource(filePath.toUri());

        // Configura la respuesta para descargar el archivo
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName)
                .body(resource);
    }
    
    
    //Metodos Nuevos para la carga de imagenes/archivos
    @PostMapping("/uploadCierre/{id}")
    public String uploadCierre(@RequestParam("file") MultipartFile file, @PathVariable String id) {
        try {
            // Verifica si el archivo está vacío
            if (file.isEmpty()) {
                return "Archivo vacío";
            }
            
            // Crea el directorio de carga si no existe
            File uploadDir = new File(UPLOAD_DIR);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            // Obtiene el nombre del archivo original
            String fileName = file.getOriginalFilename();

            // Guarda el archivo en el directorio de carga
            File destFile = new File(UPLOAD_DIR + fileName);
            FileOutputStream fos = new FileOutputStream(destFile);
            fos.write(file.getBytes());
            fos.close();

            // Obtiene la entidad desde la base de datos
            Proceso proceso = processServiceImpl.findById(id).get();
            if (proceso == null) {
                return "La entidad no existe";
            }

            // Asigna la ruta del documento compromiso a la entidad
            proceso.setActaCierre(fileName); // Asigna el nombre del archivo como ruta del documento compromiso
            processServiceImpl.save(proceso);

            return "Archivo cargado exitosamente y asociado a la entidad: " + fileName;
        } catch (IOException e) {
            return "Error al cargar el archivo";
        }
    }
    
     @GetMapping("/imagenCierre/{id}")
    public ResponseEntity<?> imagenCierre(@PathVariable String id) throws MalformedURLException {
        Proceso proceso = processServiceImpl.findById(id).orElse(null);
        if (proceso == null || proceso.getDocumentoCompromiso() == null) {
            return ResponseEntity.notFound().build();
        }

        String fileName = proceso.getActaCierre();
        Path filePath = Paths.get(UPLOAD_DIR + fileName);
        Resource resource;
        resource = new UrlResource(filePath.toUri());

        // Configura la respuesta para descargar el archivo
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName)
                .body(resource);
    }
    
    //Metodos Nuevos para la carga de imagenes/archivos
    @PostMapping("/uploadImpacto/{id}")
    public String uploadImpacto(@RequestParam("file") MultipartFile file, @PathVariable String id) {
        try {
            // Verifica si el archivo está vacío
            if (file.isEmpty()) {
                return "Archivo vacío";
            }
            
            // Crea el directorio de carga si no existe
            File uploadDir = new File(UPLOAD_DIR);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            // Obtiene el nombre del archivo original
            String fileName = file.getOriginalFilename();

            // Guarda el archivo en el directorio de carga
            File destFile = new File(UPLOAD_DIR + fileName);
            FileOutputStream fos = new FileOutputStream(destFile);
            fos.write(file.getBytes());
            fos.close();

            // Obtiene la entidad desde la base de datos
            Proceso proceso = processServiceImpl.findById(id).get();
            if (proceso == null) {
                return "La entidad no existe";
            }

            // Asigna la ruta del documento compromiso a la entidad
            proceso.setImpacto(fileName); // Asigna el nombre del archivo como ruta del documento compromiso
            processServiceImpl.save(proceso);

            return "Archivo cargado exitosamente y asociado a la entidad: " + fileName;
        } catch (IOException e) {
            return "Error al cargar el archivo";
        }
    }
    
     @GetMapping("/imagenImpacto/{id}")
    public ResponseEntity<?> imagenImpacto(@PathVariable String id) throws MalformedURLException {
        Proceso proceso = processServiceImpl.findById(id).orElse(null);
        if (proceso == null || proceso.getDocumentoCompromiso() == null) {
            return ResponseEntity.notFound().build();
        }

        String fileName = proceso.getImpacto();
        Path filePath = Paths.get(UPLOAD_DIR + fileName);
        Resource resource;
        resource = new UrlResource(filePath.toUri());

        // Configura la respuesta para descargar el archivo
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName)
                .body(resource);
    }
    
    
    
    
    private String generateUniqueFileName(String originalFileName) {
        String extension = "";
        int dotIndex = originalFileName.lastIndexOf(".");
        if (dotIndex >= 0) {
            extension = originalFileName.substring(dotIndex);
        }
        // Reemplazar espacios en blanco con guiones bajos
        String sanitizedFileName = originalFileName.replaceAll("\\s", "_");

        String uniquePart = Instant.now().toEpochMilli() + "-" + UUID.randomUUID().toString();
        return uniquePart + "-" + sanitizedFileName + extension;
    }

    private String detectContentType(String fileName) {
        String extension = FilenameUtils.getExtension(fileName);

        // Agrega lógica para detectar otros tipos de archivos según su extensión, por ejemplo, PDF, DOCX, etc.
        if ("pdf".equalsIgnoreCase(extension)) {
            return MediaType.APPLICATION_PDF_VALUE;
        } else if ("docx".equalsIgnoreCase(extension)) {
            return "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
        } else if ("jpg".equalsIgnoreCase(extension) || "jpeg".equalsIgnoreCase(extension)) {
            return MediaType.IMAGE_JPEG_VALUE;
        } else if ("png".equalsIgnoreCase(extension)) {
            return MediaType.IMAGE_PNG_VALUE;
        } else if ("gif".equalsIgnoreCase(extension)) {
            return MediaType.IMAGE_GIF_VALUE;
        }

        // Si no se reconoce la extensión, puedes devolver un tipo de contenido genérico
        return MediaType.APPLICATION_OCTET_STREAM_VALUE;
    }

}
