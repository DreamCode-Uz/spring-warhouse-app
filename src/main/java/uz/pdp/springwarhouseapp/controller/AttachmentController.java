package uz.pdp.springwarhouseapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import uz.pdp.springwarhouseapp.payload.Result;
import uz.pdp.springwarhouseapp.service.AttachmentService;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/attachment")
public class AttachmentController {
    private final AttachmentService service;

    @Autowired
    public AttachmentController(AttachmentService service) {
        this.service = service;
    }

    @PostMapping("/upload")
    public ResponseEntity<?> upload(MultipartHttpServletRequest request) {
        return service.fileUpload(request);
    }

    @GetMapping("/download/fileId={fileId}")
    public ResponseEntity<?> download(@PathVariable("fileId") Integer fileId, HttpServletResponse response) {
        return service.fileDownload(fileId, response);
    }
}
