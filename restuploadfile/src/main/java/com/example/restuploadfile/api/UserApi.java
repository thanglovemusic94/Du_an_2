package com.example.restuploadfile.api;

import com.example.restuploadfile.dto.UserDTO;
import com.example.restuploadfile.entity.UserEntity;
import com.example.restuploadfile.repository.UserRepository;
import com.example.restuploadfile.util.Converter;
import com.example.restuploadfile.util.UploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/api/user")
//@CrossOrigin(origins = "http://localhost:4200")
public class UserApi {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    public Converter converter;

    @Autowired
    private UploadUtil uploadUtil;


    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestBody UserDTO userDTO) {
        String path = File.separator + "image" + File.separator + userDTO.getNameImage();
        uploadUtil.writeFile(userDTO.getBase64(), path);

        UserEntity userEntity = converter.toEntity(userDTO);
        userEntity.setPath(path);
        userRepository.save(userEntity);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/view")
    public String uploadFile1(Model model) throws IOException {
        List<UserEntity> listEntitys = userRepository.findAll();
        model.addAttribute("user", listEntitys);
        return "user";
    }
}
