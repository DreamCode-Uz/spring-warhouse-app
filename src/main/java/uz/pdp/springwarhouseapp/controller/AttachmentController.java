package uz.pdp.springwarhouseapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import uz.pdp.springwarhouseapp.entity.Attachment;
import uz.pdp.springwarhouseapp.service.AttachmentService;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

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

    @GetMapping("/download/fileId={attachmentId}")
    public ResponseEntity<?> download(@PathVariable("attachmentId") Integer fileId, HttpServletResponse response) {
        return service.fileDownload(fileId, response);
    }

    @GetMapping()
    public ResponseEntity<List<Attachment>> getAll() {
        return service.getAllAttachment();
    }

    @GetMapping("/id={attachmentId}")
    public ResponseEntity<?> getOne(@PathVariable("attachmentId") Integer id) {
        return service.getOneAttachment(id);
    }

    @DeleteMapping("/id={attachmentId}")
    public ResponseEntity<?> delete(@PathVariable("attachmentId") Integer id) {
        return service.deleteAttachment(id);
    }
}
