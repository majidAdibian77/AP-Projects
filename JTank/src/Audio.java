/**
 * a class for manage all sound in game
 * added from JLayer Library
 *   @author : Mohammad mighani
 */

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import java.io.*;

public class Audio implements Serializable
{
    static Audio audio = null;
    private String pathBackMusic = "Sounds\\gameSound1.mp3";
    private String pathGameOver = "Sounds\\gameOver.mp3";
    private String pathvictory = "Sounds\\endOfGame.mp3";
    private static boolean backMusic = false;
    private static boolean victoryMusic = false;
    private static boolean gameOverMusic = false;
    private static String pathMenu = "Sounds\\MusicMenu.mp3";
    private static boolean menuMusic = false;
    private String pathShoot1 = "Sounds\\cannon.mp3";
    private String pathShoot2 = "Sounds\\mashingun.mp3";
    private String pathBulletCollide = "Sounds\\recosh.mp3";
    private String pathItem = "Sounds\\repair.mp3";
    private String pathEmpty = "Sounds\\emptyGun.mp3";
    private String pathMove = "Sounds\\motor1.mp3";
    private static boolean move = false;
    Player playerback;
    Player playerMenu;
    Player playerVictory;
    Player playerGameOver;

    /**
     * play music back ground
     */

    public void playBackMusic()
    {

        if (!backMusic)
        {
            backMusic = true;
            ThreadPool.execute(new Runnable()
            {
                @Override
                public void run()
                {
                    synchronized (this)
                    {
                        if (!backMusic)
                            backMusic = true;
                    }
                    if (backMusic)
                    {
                        try
                        {
                            File fileBack = new File(pathBackMusic);
                            FileInputStream fisBack  =new FileInputStream(fileBack);

                            BufferedInputStream bisBack = new BufferedInputStream(fisBack);

                            playerback = new Player(bisBack);
                            playerback.play();
                            backMusic = false;
                        } catch (JavaLayerException e)
                        {
                            e.printStackTrace();

                        } catch (FileNotFoundException e)
                        {
                            e.printStackTrace();
                        }
                    }
                }
            });


            backMusic = false;
        }

    }

    /**
     * play music game ove
     */

    public void playGameOver()
    {

        if (!gameOverMusic)
        {
            gameOverMusic = true;
            ThreadPool.execute(new Runnable()
            {
                @Override
                public void run()
                {
                    synchronized (this)
                    {
                        if (!gameOverMusic)
                            gameOverMusic = true;
                    }
                    if (gameOverMusic)
                    {
                        try
                        {
                            File fileBack = new File(pathGameOver);
                            FileInputStream fisBack  =new FileInputStream(fileBack);

                            BufferedInputStream bisBack = new BufferedInputStream(fisBack);

                            playerGameOver = new Player(bisBack);
                            playerGameOver.play();
                            gameOverMusic= false;
                        } catch (JavaLayerException e)
                        {
                            e.printStackTrace();

                        } catch (FileNotFoundException e)
                        {
                            e.printStackTrace();
                        }
                    }
                }
            });

            gameOverMusic = false;
        }

    }

    /**
     *  play victory music
     */
    public void playVictory()
    {

        if (!victoryMusic)
        {
            victoryMusic = true;
            ThreadPool.execute(new Runnable()
            {
                @Override
                public void run()
                {
                    synchronized (this)
                    {
                        if (!victoryMusic)
                            victoryMusic = true;
                    }
                    if (victoryMusic)
                    {
                        try
                        {
                            File fileBack = new File(pathvictory);
                            FileInputStream fisBack  =new FileInputStream(fileBack);

                            BufferedInputStream bisBack = new BufferedInputStream(fisBack);

                            playerVictory = new Player(bisBack);
                            playerVictory.play();
                            victoryMusic= false;
                        } catch (JavaLayerException e)
                        {
                            e.printStackTrace();

                        } catch (FileNotFoundException e)
                        {
                            e.printStackTrace();
                        }
                    }
                }
            });

            victoryMusic = false;
        }

    }

    /**
     * play menu music
     */

    public void playMenukMusic()
    {
//
//      do
//        {
        if (!menuMusic)
        {
            menuMusic = true;
            ThreadPool.execute(new Runnable()
            {
                @Override
                public void run()
                {
                    synchronized (this)
                    {
                        if (!menuMusic)
                            menuMusic = true;
                    }
                    if (menuMusic)
                    {
                        try
                        {
                            File fileMenu = new File(pathMenu);
                            FileInputStream fisMenu = null;
                            try
                            {
                                fisMenu = new FileInputStream(fileMenu);
                            } catch (FileNotFoundException e)
                            {
                                e.printStackTrace();
                            }
                            BufferedInputStream bisMenu = new BufferedInputStream(fisMenu);
                            playerMenu = new Player(bisMenu);
                            playerMenu.play();
                            menuMusic = false;
                        } catch (JavaLayerException e)
                        {
                            e.printStackTrace();

                        }
                    }
                }
            });

            menuMusic = false;
        }
//        }
//        while (loop);

    }

    /**
     * play cannon shot sound
     */
    public void playShoot1()
    {
        playSound(pathShoot1);

    }

    /**
     * play when guns is empty
     */
    public void playEmpty()
    {
        playSound(pathEmpty);

    }
    /**
     * play machin gun sound
     */

    public void playShoot2()
    {

        playSound(pathShoot2);
    }

    /**
     * play bulletcollide sound
     */
    public void playBulletCollide()
    {
        playSound(pathBulletCollide);


    }

    /**
     * play with get a item
     */
    public void playItems()
    {
        playSound(pathItem);

    }

    /**
     * play move sound
     */

    public void playMove()
    {
        if (!move)
        {
            move = true;
            ThreadPool.execute(new Runnable()
            {
                @Override
                public void run()
                {
                    synchronized (this)
                    {
                        if (!move)
                            move = true;
                    }
                    if (move)
                    {
                        try
                        {
                            File file = new File(pathMove);
                            FileInputStream fis = new FileInputStream(file);
                            BufferedInputStream bis = new BufferedInputStream(fis);
                            Player player = new Player(bis);
                            player.play();
                            move = false;
                        } catch (FileNotFoundException e)
                        {
                            e.printStackTrace();
                        } catch (JavaLayerException e)
                        {
                            e.printStackTrace();

                        }
                    }
                }
            });


            move = false;

        }
    }

    /**
     * play sound method for reduse coupling
     * @param path
     */
    private void playSound(String path)
    {
        ThreadPool.execute(new Runnable()
        {
            @Override
            public void run()
            {
                {
                    try
                    {
                        File file = new File(path);
                        FileInputStream fis = new FileInputStream(file);
                        BufferedInputStream bis = new BufferedInputStream(fis);
                        Player player = new Player(bis);
                        player.play();
                    } catch (FileNotFoundException e)
                    {
                        e.printStackTrace();
                    } catch (JavaLayerException e)
                    {
                        e.printStackTrace();

                    }
                }
            }
        });
    }

    public static Audio getAudio()
    {
        if (audio == null)
            audio = new Audio();
        return audio;
    }
}

