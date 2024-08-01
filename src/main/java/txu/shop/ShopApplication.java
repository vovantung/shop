package txu.shop;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import txu.common.grpc.GrpcServer;
import txu.shop.grpc.ShopGrpcService;
import java.io.IOException;

@SpringBootApplication
public class ShopApplication {
	public static void main(String[] args) throws IOException, InterruptedException {
		GrpcServer.start(ShopApplication.class, ShopGrpcService.class);
	}
}
