package goorm.woowa.webide.project.service;


import com.google.gson.Gson;
import goorm.woowa.webide.project.domain.ProjectLanguage;
import goorm.woowa.webide.project.util.FileUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import static goorm.woowa.webide.project.domain.ProjectLanguage.*;

@Slf4j
public class FileExecute {

    public static void executeFile(String code, ProjectLanguage language) throws IOException, InterruptedException {

        String command1, command2;
        String fileName;

        switch (language.name()) {
            case "PYTHON": {
                fileName = FileUtil.makeFileFromCode(code, language);
                command1 = "python3";
                log.info("command={}, filename={}", command1, fileName);
                commonProcess(command1, fileName);
                FileUtil.deleteFile(fileName);
                break;
            }
            case "JAVA": {
                fileName = FileUtil.makeFileFromCode(code, language);
                command1 = "javac";
                command2 = "java";
                log.info("command={}, filename={}", command1, fileName);
                commonProcess(command1, fileName);
                FileUtil.deleteFile(fileName);
                break;
            }
        }

    }

    private static String  commonProcess(String command, String fileName) throws IOException, InterruptedException {
        ProcessBuilder pb = new ProcessBuilder(command, fileName);
        Process p = pb.start();

        if (p.getInputStream().read() > 0) { // 결과값이 있으면
            return resultToJson(p.getInputStream());
        } else if (p.getErrorStream().available() > 0) { // 오류가 있으면
            return resultToJson(p.getErrorStream());
        }

        InputStream inputStream = p.getInputStream();
        log.info("결과값이 있는지={} ", inputStream.read());

        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        for (String str; (str = br.readLine()) != null; ) {
            log.info("result={}", str);
        }

        // 1. 오류 메시지 출력
        InputStream errorStream = p.getErrorStream();
        log.info("오류가 있는지={}", p.getErrorStream().available());
        BufferedReader errorReader = new BufferedReader(new InputStreamReader(errorStream));

        String errorLine;
        while ((errorLine = errorReader.readLine()) != null) {
            // 오류 처리
            System.out.println("오류: " + errorLine);
        }

        // 2. 프로세스 종료 대기
        int exitCode = p.waitFor();
        System.out.println("종료 코드: " + exitCode);

        // 3. 프로세스 강제 종료
        p.destroy();

        // 4. 종료 코드 확인
        int exitCode2 = p.exitValue();
        System.out.println("외부 프로그램 종료 코드: " + exitCode2);
    }

    private static String resultToJson(InputStream stream) throws IOException {
        Gson gson = new Gson();
        Map<Integer, String> result = new HashMap<>();

        BufferedReader br = new BufferedReader(new InputStreamReader(stream));

        int i = 1;
        for (String str; (str = br.readLine()) != null; ) {
            log.info("result={}", str);
            result.put(i++, str);
        }

        return gson.toJson(result);
    }
}
