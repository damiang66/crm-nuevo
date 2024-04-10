package com.procesos.demo.entity.emprendedor.canvas;

import lombok.Data;



@Data
public class Canvas {


    private ActividadClave actividadClave;
    private Canales canales;
    private EstructuraCosto estructuraCosto;
    private FlujoDeIngreso flujoDeIngreso;
    private PropuestaValor propuestaValor;
    private RecursoClave recursoClave;
    private RelacionCliente relacionCliente;
    private SegmentoCliente segmentoCliente;
    private SocioCLave socioCLave;
   /*
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        @OneToOne
        private CustomerSegments customerSegments;
        @OneToOne
        private ValuePropositions valuePropositions;
        @OneToOne
        private Channels channels;
        @OneToOne
        private CustomerRelationships customerRelationships;
        @OneToOne
        private KeyRecources keyRecources;
        @OneToOne
        private KeyActivities keyActivities;
        @OneToOne
        private KeyPartners keyPartners;
        @OneToOne
        private RevenueStreams revenueStreams;
        @OneToOne
        private CostStructure costStructure;
        @OneToOne
        private Client client;

*/

}
