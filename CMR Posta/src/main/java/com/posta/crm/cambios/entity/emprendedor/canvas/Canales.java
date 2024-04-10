
package com.procesos.demo.entity.emprendedor.canvas;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;


@Data
public class
Canales {


  private String informacion;
  private String evaluacion;
  private String compra;
  private String entrega;
  private String postVenta;

}
