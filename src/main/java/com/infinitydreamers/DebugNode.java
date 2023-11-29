package com.infinitydreamers;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DebugNode extends InputOutputNode {
    int successCount = 0;
    int failCount = 0;

    public DebugNode(String name) {
        super(name);
    }

    @Override
    void process() {
        if ((getInputWire(0) != null) && getInputWire(0).hasMessage()) {
            Message message = getInputWire(0).get();
            if (message.isFlag()) {
                successCount++;
            } else {
                failCount++;
            }
            log.debug("전체 : " + (successCount + failCount) + " / 성공 : " + successCount + " / 실패 : " + failCount);
        }
    }
}
