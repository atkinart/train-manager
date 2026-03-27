#pragma once

#include <SPI.h>
#include <MFRC522.h>

class ReaderAdapter {
public:
  ReaderAdapter(byte ssPin, byte rstPin) : rfid(ssPin, rstPin) {}

  void begin() {
    SPI.begin();
    rfid.PCD_Init();
  }

  bool readTag(String &uidOut) {
    if (!rfid.PICC_IsNewCardPresent() || !rfid.PICC_ReadCardSerial()) {
      return false;
    }

    uidOut = "";
    for (byte i = 0; i < rfid.uid.size; i++) {
      if (rfid.uid.uidByte[i] < 0x10) uidOut += "0";
      uidOut += String(rfid.uid.uidByte[i], HEX);
    }
    uidOut.toUpperCase();

    rfid.PICC_HaltA();
    rfid.PCD_StopCrypto1();
    return true;
  }

private:
  MFRC522 rfid;
};
