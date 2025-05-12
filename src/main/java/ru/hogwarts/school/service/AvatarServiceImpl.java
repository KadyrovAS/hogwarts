package ru.hogwarts.school.service;

import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.AvatarRepository;
import ru.hogwarts.school.repositories.StudentRepository;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
public class AvatarServiceImpl implements AvatarService {
    private static final Logger logger = LoggerFactory.getLogger(AvatarServiceImpl.class);

    private final AvatarRepository avatarRepository;
    private final StudentRepository studentRepository;

    @Value("${path.to.avatars.folder}")
    private String avatarsDir;

    public AvatarServiceImpl(AvatarRepository avatarRepository, StudentRepository studentRepository) {
        this.avatarRepository = avatarRepository;
        this.studentRepository = studentRepository;
    }

    @Override
    @Transactional
    public void uploadAvatar(Long studentId, MultipartFile avatarFile) throws IOException {
        logger.info("Was invoked method for upload avatar for student with id = {}", studentId);
        Student student = studentRepository.getById(studentId);
        Path filePath = Path.of(avatarsDir, studentId + "." + getExtensions(avatarFile.getOriginalFilename()));
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);

        try (
                InputStream is = avatarFile.getInputStream();
                OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
                BufferedInputStream bis = new BufferedInputStream(is, 1024);
                BufferedOutputStream bos = new BufferedOutputStream(os, 1024);
        ) {
            bis.transferTo(bos);
        }
        Avatar avatar = findAvatar(studentId);
        avatar.setStudent(student);
        avatar.setFilePath(filePath.toString());
        avatar.setFileSize(avatar.getFileSize());
        avatar.setMediaType(avatarFile.getContentType());
        avatar.setData(generateDataForDB(filePath));
        avatarRepository.save(avatar);
    }

    @Override
    @Transactional
    public Avatar findAvatar(Long studentId) {
        logger.info("Was invoked method for find student by id = {}", studentId);
        return avatarRepository.findByStudentId(studentId).orElse(new Avatar());
    }

    public byte[] generateDataForDB(Path filePath) throws IOException {
        logger.info("Was invoked method for generate data for DB");
        try (
                InputStream is = Files.newInputStream(filePath);
                BufferedInputStream bis = new BufferedInputStream(is, 1024);
                ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            BufferedImage image = ImageIO.read(bis);

            int height = image.getHeight() / (image.getWidth() / 100);
            BufferedImage preview = new BufferedImage(100, height, image.getType());
            Graphics2D graphics2D = preview.createGraphics();
            graphics2D.drawImage(image, 0, 0, 100, height, null);
            graphics2D.dispose();

            ImageIO.write(preview, getExtensions(filePath.getFileName().toString()), baos);
            return baos.toByteArray();
        }catch (IOException e) {
            logger.error(e.getMessage());
            throw new IOException(e);
        }
    }

    private String getExtensions(String fileName) {
        logger.info("Was invoked method for getting extensions of file {}", fileName);
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    @Transactional
    public Collection<Avatar> getAllAvatars(Integer pageNumber, Integer pageSize) {
        logger.info("Was invoked method for getting all avatars with page number {} and page size {}",
                pageNumber, pageSize);
        if (pageNumber <= 0 || pageSize <= 0) {
            return new ArrayList<>();
        }

        PageRequest pageRequest = PageRequest.of(pageNumber - 1, pageSize);
        return avatarRepository.findAll(pageRequest).getContent();
    }
}
