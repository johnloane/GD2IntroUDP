package com.dkit.gd2.johnloane;

import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;

/**
 * First Sockets program
 * Uses UDP - Connectionless Sockets, like sending postcards
 * DatagramSocket = UDP
 * Socket = TCP
 */
public class Sender
{
    public static void main( String[] args )
    {
        DatagramSocket senderSocket = null;

        InetAddress receiverHost = null;

        try
        {
            receiverHost = InetAddress.getLocalHost();

            int receiverPort = 50001;
            DatagramPacket datagram = null;

            senderSocket = initializeSenderSocket(senderSocket, receiverHost, receiverPort);
            datagram = initializeDatagramPacket(datagram, receiverHost, receiverPort);
            senderSocket.send(datagram);
            System.out.println("Datagram sent using UDP socket");
            getEchoResponseFromReceiver(senderSocket);

        } catch (UnknownHostException e)
        {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private static void getEchoResponseFromReceiver(DatagramSocket senderSocket)
    {
        byte[] response = new byte[70];
        DatagramPacket echo = new DatagramPacket(response, response.length);

        //Receive the response
        //The senderSocket is blocking -> it will wait for a response before it continues
        try
        {
            senderSocket.receive(echo);
        } catch (IOException e)
        {
            e.printStackTrace();
        }

        //Get the content of the response
        String responseMessage = new String(echo.getData());

        //Display the data received
        System.out.println("Response: " + responseMessage);

    }

    private static DatagramPacket initializeDatagramPacket(DatagramPacket datagram, InetAddress receiverHost, int receiverPort)
    {
        //Message to be sent
        String message = "GD2 first network application using UDP sockets";
        byte buffer[] = message.getBytes();
        //Build the datagram. Needs
        //buffer -> data to be sent
        //buffer.length -> how much data to send
        //receiverHost -> ip address to send to
        //receiverPort -> port number that the receiver is listening on
        datagram = new DatagramPacket(buffer, buffer.length, receiverHost, receiverPort);
        return datagram;

    }

    private static DatagramSocket initializeSenderSocket(DatagramSocket senderSocket, InetAddress receiverHost, int receiverPort)
    {
        try
        {
            //Create a port for the sender to listen on and receive into
            int senderPort = 50000;

            receiverPort = 50001;
            senderSocket = new DatagramSocket(senderPort);
        } catch (SocketException e)
        {
            e.printStackTrace();
        }
        return senderSocket;
    }
}
