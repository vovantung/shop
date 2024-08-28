package txu.shop.dto;

import lombok.Getter;

@Getter
public class UpdateNoteRequest {
    String orderId;
    String note;
}
