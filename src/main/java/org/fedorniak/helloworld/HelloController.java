package org.fedorniak.helloworld;

import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.Objects;
import java.util.concurrent.*;

public class HelloController {

    private final int ROTATION_SLEEP_MS = 50;   // for the rotation smoothness
    private final int ROTATION_MIN_ANGLE = 5;   // min rotation speed
    private final int ROTATION_MAX_ANGLE = 100; // max rotation speed

    private final ExecutorService executor;

    private final Label speedText;
    private final Rectangle rec;

    private Future<?> rotationProc;

    public HelloController(Label speedText, Rectangle rec) {
        this.speedText = speedText;
        this.rec = rec;
        this.executor = Executors.newSingleThreadExecutor();
    }

    public void onRotateButtonClick() {
        stopRotation();
        int speed = ThreadLocalRandom.current().nextInt(ROTATION_MIN_ANGLE, ROTATION_MAX_ANGLE);
        updateSpeedLabel(speed);
        setRandomColorToRec();
        rotationProc = executor.submit(
                () -> this.rotateRectangle(speed));
    }

    public void onStopButtonClick() {
        stopRotation();
    }

    public void onResetButtonClick() {
        stopRotation();
        rec.setRotate(0d);
    }

    private void stopRotation() {
        if (Objects.nonNull(rotationProc) && !rotationProc.isDone())
            rotationProc.cancel(true);
    }

    private void setRandomColorToRec() {
        rec.setFill(Color.rgb(
                ThreadLocalRandom.current().nextInt(255),
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

    private void updateSpeedLabel(int speed) {
        speedText.setText("Speed: " + speed);
    }
}