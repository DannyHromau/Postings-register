package com.dannyhromau.main.dao.parser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;


public class ParserCsv implements TableParser {

    public List<String> getParsedLines(String path) {
        List<String> dataToDbList = new ArrayList<>();
        try {
            List<String> linesFromFile = Files.readAllLines(Path.of(path));
            for (String data : linesFromFile) {
                if (data.isEmpty() || data.equals(linesFromFile.get(0))) {
                    continue;
                }
                data = data.replaceAll(",", ".");
                data = data.replaceAll("\t", "");
                data = data.replaceAll("[;]", "\t");
                dataToDbList.add(data);
            }

        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        return dataToDbList;
    }
}
