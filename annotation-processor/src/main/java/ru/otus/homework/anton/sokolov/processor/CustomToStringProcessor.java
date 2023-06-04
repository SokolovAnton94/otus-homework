package ru.otus.homework.anton.sokolov.processor;


import com.google.auto.service.AutoService;
import ru.otus.homework.anton.sokolov.annotation.CustomToString;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Writer;
import java.util.Set;

@SupportedAnnotationTypes("ru.otus.homework.anton.sokolov.annotation.CustomToString")
@SupportedSourceVersion(SourceVersion.RELEASE_20)
@AutoService(Processor.class)
public class CustomToStringProcessor extends AbstractProcessor {

    private static final String TO_STRING_METHOD = """
                @Override
                public String toString() {
                    return "Custom toString";
                }
            }""";

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (Element element1 : roundEnv.getElementsAnnotatedWith(CustomToString.class)) {
            TypeElement element = (TypeElement) element1;
            try {
                addToStringMethod(element);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return true;
    }

    private void addToStringMethod(TypeElement element) throws IOException {
        JavaFileObject file = processingEnv.getElementUtils().getFileObjectOf(element);
        StringBuilder sb = new StringBuilder();
        try (BufferedReader bf = new BufferedReader(file.openReader(true))) {
            while (true) {
                String line = bf.readLine();
                if (line != null) {
                    sb.append(line);
                } else {
                    break;
                }
                sb.append("\n");
            }

            if (sb.toString().contains("toString()")) {
                return;
            }

            int i = sb.lastIndexOf("}");
            sb.deleteCharAt(i);
            sb.append(TO_STRING_METHOD);
        }
        try (Writer out = file.openWriter()) {
            out.write(sb.toString());
        }
    }
}