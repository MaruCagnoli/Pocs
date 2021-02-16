package com.poc.PocJPAyRedis.services;

import com.poc.PocJPAyRedis.models.RealState;
import com.poc.PocJPAyRedis.models.RealStateResponse;
import com.poc.PocJPAyRedis.repositories.RealStateRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class RealStateService {

   private final String url = "https://www.dantepropiedades.com/";
    @Autowired
   private RealStateRepository realStateRepository;

    public String startExtractingInformation() {

        //List<String> links = extractRealStateList();
        RealState real = buildRealState("https://www.dantepropiedades.com/departamento-venta-la-perla_374912_propiedad-inmobiliaria.html");
        System.out.println(real);
        return "hola a todos venimos a scrapear";

    }
   private static void delaySeconds(){
       try{
           Thread.sleep(1000);
       }catch(InterruptedException e){
           e.printStackTrace();
       }
   }
   private static Optional<Document> getDom(String url) {
        try{
            return Optional.of(Jsoup.connect(url).userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/77.0.3865.90 Safari/537.36  ").get());
        }catch(IOException e){
            return Optional.empty();
        }
   }
   private List<String> extractRealStateList(){
       Optional<Document> op = getDom(this.url);
       List<String> realStatesLinks = new ArrayList<>();
       var elementsList = op.map(dom -> dom.select(".container")
               .select(".row")
               .select(".col-lg-4.col-md-4.col-sm-6.col-xs-12.colProp"));
       if(elementsList.isPresent()){
           elementsList.get().forEach(element -> {
               String link = element.select(".propiedadDestacada").attr("abs:href").toString();
               realStatesLinks.add(link);
           });
       }
       System.out.println(realStatesLinks);
       return realStatesLinks;

   }
   private RealStateResponse buildAndPersistRealStates(List<String> links){
        List<RealState> realStates = new ArrayList<>();
        links.forEach(l -> {
            RealState realState = buildRealState(l);
            System.out.println(realState);
            realStates.add(realState);
            //TODO persist real state
        });
        return RealStateResponse.builder().realStates(realStates).build();
   }
   public RealState buildRealState(String urlRealState) {
        Optional<Document> realStateDocument = getDom(urlRealState);
        var listLi = realStateDocument
                .map(dom -> dom
                        .select(".ListaDatosDetalle")
                        .select("li"));
        HashMap<String, String> detailsOfRealState = new HashMap<>();
        if (listLi.isPresent()){
            var elements = listLi.get();
            elements.forEach(e -> {
                String key = e.child(1).childNode(0).toString();
                String value = "";
                if(e.childNodeSize() == 3){
                    value =  e.child(0).parentNode().childNode(2).toString();
                }
                detailsOfRealState.put(key, value);
            });
            System.out.println(detailsOfRealState);
        }
       System.out.println(processDetailsOfRealState(detailsOfRealState));
        RealState realState = new RealState();

        return realState;

   }
   public List<String> processDetailsOfRealState(HashMap<String, String> details) {
        List<String> detailsOfRealState = new ArrayList<>();
        details.forEach((k,v) -> {
                    if(v.isBlank()){
                        k = k.replace(": ","").trim();
                        detailsOfRealState.add(k);
                    }else{
                        k = k + v;
                        detailsOfRealState.add(k.trim());
                    }
                }
        );
        return detailsOfRealState;
   }


}
/*
[Reciclado ,Vista al Mar A, Tipo de Operación: Venta,
Baños: 2, Cochera , Superficie Total (m2), Balcón , Zona: La Perla, Ambientes: 3, Valor,
Dirección Aprox.: Diagonal Alberdi 2392, Disposición: Frente, Vista Panorámica ,
 Dormitorios: 2, Ultima Actualizacion: 06/01/2021]
*/
