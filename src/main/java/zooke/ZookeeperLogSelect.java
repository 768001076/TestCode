package zooke;

import org.apache.jute.BinaryInputArchive;
import org.apache.jute.Record;
import org.apache.zookeeper.server.LogFormatter;
import org.apache.zookeeper.server.TraceFormatter;
import org.apache.zookeeper.server.persistence.FileHeader;
import org.apache.zookeeper.server.persistence.FileTxnLog;
import org.apache.zookeeper.server.util.SerializeUtils;
import org.apache.zookeeper.txn.TxnHeader;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.zip.Adler32;
import java.util.zip.Checksum;

public class ZookeeperLogSelect {

    public static void main(String[] args) throws Exception {

        FileInputStream fis = new FileInputStream("C:\\Users\\Administrator\\Desktop\\asdasd.164dc82");
        BinaryInputArchive logStream = BinaryInputArchive.getArchive(fis);
        FileHeader fhdr = new FileHeader();
        fhdr.deserialize(logStream, "fileheader");
        if (fhdr.getMagic() != FileTxnLog.TXNLOG_MAGIC) {
            System.err.println("Invalid magic number for " + args[0]);
            System.exit(2);
        }

        System.out.println("ZooKeeper Transactional Log File with dbid " + fhdr.getDbid() + " txnlog format version " + fhdr.getVersion());
        int count = 0;

        while(true) {
            long crcValue;
            byte[] bytes;
            try {
                crcValue = logStream.readLong("crcvalue");
                bytes = logStream.readBuffer("txnEntry");
            } catch (EOFException var11) {
                System.out.println("EOF reached after " + count + " txns.");
                return;
            }

            if (bytes.length == 0) {
                System.out.println("EOF reached after " + count + " txns.");
                return;
            }

            Checksum crc = new Adler32();
            crc.update(bytes, 0, bytes.length);
            if (crcValue != crc.getValue()) {
                throw new IOException("CRC doesn't match " + crcValue + " vs " + crc.getValue());
            }

            TxnHeader hdr = new TxnHeader();
            Record txn = SerializeUtils.deserializeTxn(bytes, hdr);
            System.out.println(DateFormat.getDateTimeInstance(3, 1).format(new Date(hdr.getTime())) + " session 0x" + Long.toHexString(hdr.getClientId()) + " cxid 0x" + Long.toHexString((long)hdr.getCxid()) + " zxid 0x" + Long.toHexString(hdr.getZxid()) + " " + op2String(hdr.getType()) + " " + txn);
            if (logStream.readByte("EOR") != 66) {
                throw new EOFException("Last transaction was partial.");
            }

            ++count;
        }

    }

    static String op2String(int op) {
        switch(op) {
        case -11:
            return "closeSession";
        case -10:
            return "createSession";
        case -9:
        case -8:
        case -7:
        case -6:
        case -5:
        case -4:
        case -3:
        case -2:
        case 9:
        case 10:
        case 13:
        default:
            return "unknown " + op;
        case -1:
            return "error";
        case 0:
            return "notification";
        case 1:
            return "create";
        case 2:
            return "delete";
        case 3:
            return "exists";
        case 4:
            return "getDate";
        case 5:
            return "setData";
        case 6:
            return "getACL";
        case 7:
            return "setACL";
        case 8:
            return "getChildren";
        case 11:
            return "ping";
        case 12:
            return "getChildren2";
        case 14:
            return "multi";
        }
    }

}
