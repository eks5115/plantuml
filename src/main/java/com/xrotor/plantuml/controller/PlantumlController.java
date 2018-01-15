package com.xrotor.plantuml.controller;

import net.sourceforge.plantuml.FileFormat;
import net.sourceforge.plantuml.FileFormatOption;
import net.sourceforge.plantuml.SourceStringReader;
import net.sourceforge.plantuml.code.AsciiEncoder;
import net.sourceforge.plantuml.code.Transcoder;
import net.sourceforge.plantuml.code.TranscoderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

/**
 *
 * @author eks5115
 */
@RestController()
public class PlantumlController {

    @RequestMapping(value = "/svg/{encodeText}", method = RequestMethod.GET, produces="image/svg+xml")
    public String translateEncodeTextToSvg(@PathVariable String encodeText) throws IOException {

        Transcoder transcoder = TranscoderUtil.getDefaultTranscoder();
        String decodeText = transcoder.decode(encodeText);

        return translate(decodeText);

    }

    @RequestMapping(value = "/svg", method = RequestMethod.GET, produces="image/svg+xml")
    public String translateTextToSvg(HttpServletRequest request) throws IOException {

        String queryString = request.getQueryString();
        if (queryString.equals(new String(""))) {
            queryString = "@startuml;@enduml;";
        }

        String decodeQueryString = URLDecoder.decode(queryString, "utf-8");
        return translate(decodeQueryString.replaceAll(";", "\n"));
    }

    /**
     * translate plantuml string "@startuml;@enduml"
     * @param text
     * @return
     * @throws IOException
     */
    private String translate(String text) throws IOException {

        SourceStringReader reader = new SourceStringReader(text);
        final ByteArrayOutputStream os = new ByteArrayOutputStream();

        String desc = reader.generateImage(os, new FileFormatOption(FileFormat.SVG));

        os.close();

        return new String(os.toByteArray(), Charset.forName("UTF-8"));

    }

}
