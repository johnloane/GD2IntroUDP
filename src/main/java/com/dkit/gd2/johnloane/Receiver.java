package com.dkit.gd2.johnloane;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class Receiver
{
    public static void main(String[] args)
    {
        int receiverPort = 50001;
        int senderPort = 50000;

        boolean continueRunning = true;

        final int MAX_LEN = 70;
        DatagramSocket receiverSocket = null;

        try
        {
            receiverSocket = new DatagramSocket(receiverPort);

            System.out.println("Waiting for message on port " + receiverPort + " .....");

            while(continueRunning)
            {
                byte buffer[] = new byte[MAX_LEN];

                DatagramPacket receivedData = new DatagramPacket(buffer, MAX_LEN);

                receiverSocket.receive(receivedData);

                String message = new String(buffer);

                System.out.println("Message received: " + message);

                //Figure out where the message came from
                InetAddress sender = receivedData.getAddress();
                System.out.println("Sender: " + sender);

                String responseMessage = message;

                byte[] responseArray = responseMessage.getBytes();
                DatagramPacket response = new DatagramPacket(responseArray, responseArray.length, sender, senderPort);
                receiverSocket.send(response);
            }

        } catch (SocketException e)
        {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            if(receiverSocket != null)
            {
                receiverSocket.close();
            }
        }
    }
}
