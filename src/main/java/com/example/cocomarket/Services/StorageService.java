package com.example.cocomarket.Services;

import com.example.cocomarket.Entity.ImageData;
import com.example.cocomarket.Entity.Produit;
import com.example.cocomarket.Repository.Produit__Repository;
import com.example.cocomarket.Repository.StorageRepository;
import com.example.cocomarket.util.ImageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.Set;

@Service
public class StorageService {

    @Autowired
    private StorageRepository repository;



    @Autowired
    private Produit__Repository repoproduit ;

    public String uploadImage(MultipartFile file , Integer id ) throws IOException {

        Produit p = repoproduit.findById(id).orElse(null) ;
        Set<ImageData> List=  p.getImage();
        ImageData imageData= ImageData.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .imageData(ImageUtils.compressImage(file.getBytes())).build();

        if (imageData != null) {
          /*  for(ImageData im:List){
                im.setProduit(p);
                repository.save(im);

            }*/
            imageData.setProduit(p) ;
            repository.save(imageData) ;


            //p.getImage().add(imageData);
            // repoproduit.save(p) ;
            return "file uploaded successfully : " + file.getOriginalFilename();
        }
        return null ;
    }



    public byte[] downloadImage(String fileName) {
        Optional<ImageData> dbImageData = repository.findByName(fileName);
        byte[] images = ImageUtils.decompressImage(dbImageData.get().getImageData());
        return images;
    }


    public String upload(MultipartFile file )throws IllegalStateException, IOException {
        file.transferTo(new File("C:\\uploads\\"+ file.getOriginalFilename()));
        return ("file uploaded with succes");
    }



}