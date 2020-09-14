#include "Arduino.h"
#include "SMDjoystick.h"

SMDjoystick j(2, 3, 4, 5, 6, 7, 8);

void setup() {

	Serial.begin(115200);
}

uint16_t last = 0;

void loop() {
	uint16_t now = j.read();
	//if (last != now) {
	///char buf[5];
	///sprintf(buf, "%05hu", now);
	///Serial.println(buf);
	Serial.println(now);
	//}
	last = now;
}
