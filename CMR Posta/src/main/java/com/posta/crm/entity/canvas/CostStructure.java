package com.posta.crm.entity.canvas;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.List;
import lombok.Data;

@Entity
@Data
public class CostStructure {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @OneToMany
    private List<CostComponent> costosFijos;
    @OneToMany
    private List<CostComponent> costosVariables;
    private Double totalCostosFijos=0.0;
    private Double totalCostosVariables=0.0;
    private Double totalCostos=0.0;
    
    
    public void calcularTotales(){
            this.totalCostosFijos=0.0;
             this.totalCostosVariables=0.0;
              this.totalCostos=0.0;
        
        for (CostComponent costosFijo : costosFijos) {
            this.totalCostosFijos+=costosFijo.getAmount();
        }
        for (CostComponent costosVariable : costosVariables) {
            this.totalCostosVariables+=costosVariable.getAmount();
            
        }
        
        this.totalCostos=this.totalCostosFijos+this.totalCostosVariables;
    }
}
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
//    @OneToMany
//    @JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
//    private List<CostComponent> variableCosts;
//    @OneToMany
//    @JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
//    private List<CostComponent> fixedCosts;
//    //@NotNull
//    private Double totalVariableCosts;
//   // @NotNull
//    private Double totalfixedCosts;
//  //  @NotNull
//    private Double totalCost;
//
//
//    public CostStructure() {
//        this.variableCosts= new ArrayList<>();
//        this.fixedCosts = new ArrayList<>();
//    }
//
//    public void calculo() {
//        double sumVariableCosts = 0.0;
//        for (CostComponent costComponent : variableCosts) {
//            sumVariableCosts += costComponent.getAmount();
//        }
//
//// Asignar la suma a totalVariableCosts
//        totalVariableCosts = sumVariableCosts;
//
//// Sumar los valores de fixedCosts
//        double sumFixedCosts = 0.0;
//        for (CostComponent costComponent : fixedCosts) {
//            sumFixedCosts += costComponent.getAmount();
//        }
//
//// Asignar la suma a totalfixedCosts
//        totalfixedCosts = sumFixedCosts;
//
//// Calcular el totalCost sumando totalfixedCosts y totalVariableCosts
//        totalCost = totalfixedCosts + totalVariableCosts;
//    }
//
//}
////    public void calculo() {
////        double sumVariableCosts = 0.0;
////        for (CostComponent costComponent : variableCosts) {
////            sumVariableCosts += costComponent.getAmount();
////        }
////
////// Asignar la suma a totalVariableCosts
////        totalVariableCosts = sumVariableCosts;
////
////// Sumar los valores de fixedCosts
////        double sumFixedCosts = 0.0;
////        for (CostComponent costComponent : fixedCosts) {
////            sumFixedCosts += costComponent.getAmount();
////        }
////
////// Asignar la suma a totalfixedCosts
////        totalfixedCosts = sumFixedCosts;
////
////// Calcular el totalCost sumando totalfixedCosts y totalVariableCosts
////        totalCost = totalfixedCosts + totalVariableCosts;
////    }

