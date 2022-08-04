package uz.pdp.springwarhouseapp.service;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import uz.pdp.springwarhouseapp.entity.Attachment;
import uz.pdp.springwarhouseapp.entity.AttachmentContent;
import uz.pdp.springwarhouseapp.repository.AttachmentContentRepository;
import uz.pdp.springwarhouseapp.repository.AttachmentRepository;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

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
                return new ResponseEntity<>("File saved id=" + savedContent.getId(), CREATED);
            } catch (IOException e) {
                repository.delete(attachment);
                return new ResponseEntity<>("Failed to convert file to byte.", EXPECTATION_FAILED);
            }
        }
        return new ResponseEntity<>("The file does not exist.", NOT_FOUND);
    }

    public ResponseEntity<?> fileDownload(Integer fileId, HttpServletResponse response) {
        Optional<Attachment> optionalAttachment = repository.findById(fileId);
        if (optionalAttachment.isPresent()) {
            Attachment attachment = optionalAttachment.get();
            Optional<AttachmentContent> contentOptional = contentRepository.findByAttachmentId(attachment.getId());
            if (contentOptional.isPresent()) {
                response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + attachment.getName() + "\"");
                response.setContentType(attachment.getContentType());
                try {
                    FileCopyUtils.copy(contentOptional.get().getBytes(), response.getOutputStream());
                } catch (IOException e) {
                    //  TODO: 403 or 424
                    return new ResponseEntity<>(e.getMessage(), FAILED_DEPENDENCY);
                }
            } else {
                return new ResponseEntity<>("Attachment content not found.", NOT_FOUND);
            }
        }
        return new ResponseEntity<>(OK);
    }

    public ResponseEntity<List<Attachment>> getAllAttachment() {
        return new ResponseEntity<>(repository.findAll(), OK);
    }

    public ResponseEntity<?> getOneAttachment(Integer fileId) {
        Optional<Attachment> optionalAttachment = repository.findById(fileId);
        if (optionalAttachment.isPresent()) {
            return new ResponseEntity<>(optionalAttachment.get(), OK);
        }
        return new ResponseEntity<>("Attachment not found", NOT_FOUND);
    }

    public ResponseEntity<?> deleteAttachment(Integer id) {
        Optional<Attachment> optionalAttachment = repository.findById(id);
        if (optionalAttachment.isPresent()) {
            Optional<AttachmentContent> optionalContent = contentRepository.findByAttachmentId(optionalAttachment.get().getId());
            repository.delete(optionalAttachment.get());
            optionalContent.ifPresent(contentRepository::delete);
            return new ResponseEntity<>("Attachment successfully deleted", OK);
        }
        return new ResponseEntity<>("Attachment not found", NOT_FOUND);
    }
}
