package de.hofuniversity.minf.stundenplaner.common.excel;

import java.util.Map;

public interface ExcelImportable {

    void readValueMap(Map<String, String> map);
}
