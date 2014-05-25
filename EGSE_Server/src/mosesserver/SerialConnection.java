/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mosesserver;

import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;

/**
 *
 * @author SSEL
 */
public class SerialConnection {
    
    private SerialPort serialPort;
    private CommPortIdentifier portId = null;
    private InputStream RxStream = null;
    private OutputStream TxStream = null;
        
    public SerialConnection(String in_port, int in_baud, int dataBits, int stopBits, int parityBit) throws Exception
    {
        Enumeration portEnum = CommPortIdentifier.getPortIdentifiers();
        
        while(portEnum.hasMoreElements())
        {
            CommPortIdentifier currPortId = (CommPortIdentifier) portEnum.nextElement();
            if (currPortId.getName().equals(in_port))
            {
                portId = currPortId;
                break;
            }
        }
        
        if(portId.equals(null))
        {
            throw new Exception("Invalid Com port: " + in_port);
        }
        
        serialPort = (SerialPort) portId.open(this.getClass().getName(), 2000);
        
        serialPort.setSerialPortParams(in_baud,
                                        dataBits,
                                        stopBits,
                                        parityBit);
        
        RxStream = serialPort.getInputStream();
        TxStream = serialPort.getOutputStream();
    }
    
    public void close()
    {
        serialPort.close();
    }
    
    public InputStream getInputStream() throws IOException
    {
        return RxStream;
    }
    
    public void write(byte[] data) throws IOException
    {
        TxStream.write(data);
    }
}