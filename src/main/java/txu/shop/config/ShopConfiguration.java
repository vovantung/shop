package txu.shop.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import txu.common.grpc.GrpcConfig;

@Component
public class ShopConfiguration implements GrpcConfig {

    @Value("${server.grpc.port}")
    private int grpcPort;
    @Override
    public int getGrpcPort() {
        return grpcPort;
    }
}
