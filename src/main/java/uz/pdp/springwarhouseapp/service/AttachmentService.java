package uz.pdp.springwarhouseapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import uz.pdp.springwarhouseapp.entity.Attachment;
import uz.pdp.springwarhouseapp.entity.AttachmentContent;
import uz.pdp.springwarhouseapp.repository.AttachmentContentRepository;
import uz.pdp.springwarhouseapp.repository.AttachmentRepository;

import java.io.IOException;
import java.util.Iterator;

import static org.springframework.http.HttpStatus.*;

@Service
public class AttachmentService {
    private final AttachmentRepository repository;
    private final AttachmentContentRepository contentRepository;

    @Autowired
    public AttachmentService(AttachmentRepository repository, AttachmentContentRepository contentRepository) {
        this.repository = repository;
        this.contentRepository = contentRepository;
    }

    public ResponseEntity<?> fileUpload(MultipartHttpServletRequest request) {
        Iterator<String> fileNames = request.getFileNames();
        MultipartFile file = request.getFile(fileNames.next());
        if (file != null) {
            Attachment attachment = new Attachment(file.getOriginalFilename(), file.getSize(), file.getContentType());
            attachment = repository.save(attachment);

            try {
                AttachmentContent attachmentContent = new AttachmentContent(file.getBytes(), attachment);
                AttachmentContent savedContent = contentRepository.save(attachmentContent);
                return new ResponseEntity<>("File saved id="+savedContent.getId(), CREATED);
            } catch (IOException e) {
                repository.delete(attachment);
                return new ResponseEntity<>("Failed to convert file to byte.", EXPECTATION_FAILED);
            }
        }
        return new ResponseEntity<>("The file does not exist.", NOT_FOUND);
    }
}
