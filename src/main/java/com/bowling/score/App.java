package com.bowling.score;

import java.io.*;

            public class Bowling {

                static final int MAXFRAMES = 10;
                static final int MAXROLLS = (MAXFRAMES * 2 + 1);
                static int[] rolls = new int[MAXROLLS];
                static Frame[] frames = new Frame[MAXFRAMES];

                static final Bowling b = new Bowling();

                class Frame {
                    public int[] rollindex = new int[3];

                    public Frame() {
                        for (int i = 0; i < 3; i++) {
                            rollindex[i] = -1;
                        }
                    }

                    public int getTotal() {
                        int tot = 0;
                        for (int i = 0; i < 3; i++) {
                            if (rollindex[i] != -1) {
                                tot += rolls[rollindex[i]];
                            }

                        }
                        return tot;
                    }

                    public Frame newFrame() {
                        return new Frame();
                    }

                    public static void main(String[] args)
                            throws IOException {

                        String inputline;
                        int nextroll = 0;
                        int i, pinsleft, hitpins;
                        boolean extrarolls = false;

                        for (i = 0; i < MAXROLLS; i++) {
                            rolls[i] = 0;
                        }

                        BufferedReader bufrd =
                                new BufferedReader(
                                        new InputStreamReader(System.in));

                        nextframe:
                        for (int frame = 0; frame < MAXFRAMES; frame++) {

                            frames[frame] = b.newFrame();
                            pinsleft = 10;
                            for (int roll = 0; roll < 3; roll++) {

                                // Get number of pins hit from user
                                while (true) {
                                    System.out.println(
                                            "Frame " + (frame + 1) +
                                                    ", roll " + (roll + 1) +
                                                    ", pins left " + pinsleft +
                                                    ". How many hit?");
                                    inputline = bufrd.readLine();
                                    try {
                                        hitpins =
                                                Integer.parseInt(inputline);
                                    } catch (NumberFormatException e) {
                                        continue;
                                    }
                                    if ((hitpins >= 0) &&
                                            (hitpins <= pinsleft)) {
                                        break;
                                    }
                                }

                                rolls[nextroll] = hitpins;
                                frames[frame].rollindex[roll] =
                                        nextroll;

                                // If all pins down and this is not an
                                // extra roll, set it to add bonus rolls
                                int frametot = frames[frame].getTotal();
                                if ((frametot == 10) &&
                                        (extrarolls == false)) {
                                    for (int t = roll + 1; t < 3; t++) {
                                        frames[frame].rollindex[i] =
                                                nextroll + (i - roll);
                                    }
                                    ++nextroll;
                                    pinsleft -= hitpins;

                                    // two rolls, pins left, frame over
                                    if ((roll == 1) &&
                                            (frametot < 10)) {
                                        continue nextframe;
                                    }

                                    // all pins knocked down...
                                    if (frametot == 10) {
                                        if (frame < (MAXFRAMES - 1)) {
                                            continue nextframe;
                                        } else {
                                            // ...and last frame
                                            extrarolls = true;
                                        }
                                    }
                                    if (extrarolls && (pinsleft == 0)) {
                                        // new pins if needed
                                        pinsleft = 10;
                                    }
                                }

                                int total = 0;
                                for (i = 0; i < MAXFRAMES; i++) {
                                    total += frames[i].getTotal();
                                }
                                System.out.println("Game total is " + total);
                            }
                        }
                    }
                }
            }