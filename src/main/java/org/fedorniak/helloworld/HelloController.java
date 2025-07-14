package org.fedorniak.helloworld;

import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class HelloController {

    private final int ROTATION_SLEEP_MS = 50; // for the rotation smoothness
    private final int ROTATION_MIN_ANGLE = 5;
    private final int ROTATION_MAX_ANGLE = 100;

    private final Label speedText;

    private final Rectangle rec;

    private Future<?> rotationProc;

    public HelloController(Label speedText, Rectangle rec) {
        this.speedText = speedText;
        this.rec = rec;
    }

    public void onRotateButtonClick() {
        int speed = ThreadLocalRandom.current().nextInt(ROTATION_MIN_ANGLE, ROTATION_MAX_ANGLE);
        speedText.setText("Speed: " + speed);
        setRandomColorToRec();
        onStopButtonClick();
        rotationProc = Executors.newSingleThreadScheduledExecutor().submit(
                () -> {
                    this.rotateRectangle(speed);
                    Thread.currentThread().setDaemon(true);
                });
    }

    public void onStopButtonClick() {
        if (Objects.nonNull(rotationProc) && !rotationProc.isDone())
            rotationProc.cancel(true);
    }

    public void onResetButtonClick() {
        onStopButtonClick();
        rec.setRotate(0d);
    }

    private void setRandomColorToRec() {
        rec.setFill(Color.rgb(ThreadLocalRandom.current().nextInt(255),
                ThreadLocalRandom.current().nextInt(255),
                ThreadLocalRandom.current().nextInt(255)));
    }

    private void rotateRectangle(int speed) {
        while (speed > 0) {
            rec.setRotate(rec.getRotate() + speed);
            speed--;
            try {
                TimeUnit.MILLISECONDS.sleep(ROTATION_SLEEP_MS);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}