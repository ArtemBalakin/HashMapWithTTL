package Application.Utils;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.tika.config.TikaConfig;
import org.apache.tika.detect.Detector;
import org.apache.tika.io.TikaInputStream;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MediaType;

/**
 * Данный класс нужен для определения типа файла для отправки пользователю
 */
public class MIME {

    public static MediaType getMediaType (String filePath) throws Exception {

        TikaConfig config = TikaConfig.getDefaultConfig();
        Detector detector = config.getDetector();
        File file=new File(filePath);
        Path path= Paths.get(filePath);
        TikaInputStream stream = TikaInputStream.get(path);

        Metadata metadata = new Metadata();
        metadata.add(Metadata.CONTENT_TYPE,file.getName() );
        return detector.detect(stream, metadata);
    }
}
