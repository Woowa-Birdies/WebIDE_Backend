//package goorm.woowa.webide.efs.service;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//import software.amazon.awssdk.services.efs.EfsClient;
//import software.amazon.awssdk.services.efs.model.*;
//
//
//@Service
//@RequiredArgsConstructor
//public class EfsService implements EfsUseCase{
//
//    private final EfsClient efsClient;
//    @Value("${aws.efs.fileSystemId}")
//    private String fileSystemId;
//
//    public String createEFSAccessPoint(String projectId) {
//
//        // 액세스 포인트 생성 요청
//        CreateAccessPointResponse createAccessPointResponse = efsClient.createAccessPoint(CreateAccessPointRequest.builder()
//                .fileSystemId(fileSystemId)
//                .rootDirectory(request -> request.path("/projects/" + projectId))
//                .build());
//
//        // 생성된 액세스 포인트 반환
//        return createAccessPointResponse.accessPointId();
//    }
//}
