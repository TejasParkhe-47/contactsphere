package com.ContactSphere.helpers;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Message {

    private String content;

    @Builder.Default
    private MessageType type = MessageType.blue;


}
