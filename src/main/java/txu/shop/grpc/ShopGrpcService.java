package txu.shop.grpc;

import com.tx.GetMovieReply;
import com.tx.GetMovieRequest;
import com.tx.MovieGRPCServiceGrpc;
import io.grpc.stub.StreamObserver;
import org.springframework.stereotype.Service;
import txu.common.exception.NotFoundException;
//import txu.shop.exception.NotFoundException;

@Service
public class ShopGrpcService extends MovieGRPCServiceGrpc.MovieGRPCServiceImplBase {
    @Override
    public void getMovie(GetMovieRequest request, StreamObserver<GetMovieReply> responseObserver) {
        throw new NotFoundException("test exception grpc");
//        super.getMovie(request, responseObserver);
    }
}
