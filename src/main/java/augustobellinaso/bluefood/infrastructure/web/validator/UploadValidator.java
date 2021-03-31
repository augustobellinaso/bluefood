package augustobellinaso.bluefood.infrastructure.web.validator;

import augustobellinaso.bluefood.util.FileType;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;

public class UploadValidator implements ConstraintValidator<UploadConstraint, MultipartFile> {

    private List<FileType> acceptedFilesTypes;

    @Override
    public void initialize(UploadConstraint constraintAnnotation) {
        acceptedFilesTypes = Arrays.asList(constraintAnnotation.acceptedTypes());
    }

    @Override
    public boolean isValid(MultipartFile multipartFile, ConstraintValidatorContext context) {
        if (multipartFile == null) {
            return true;
        }

        for (FileType fileType : acceptedFilesTypes) {
            if (fileType.sameOf(multipartFile.getContentType())) {
                return true;
            }
        }

        return false;
    }
}
