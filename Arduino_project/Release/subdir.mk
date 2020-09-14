################################################################################
# Automatically-generated file. Do not edit!
################################################################################

# Add inputs and outputs from these tool invocations to the build variables 
INO_SRCS += \
..\GamePad.ino 

CPP_SRCS += \
..\.ino.cpp \
..\SMDjoystick.cpp 

LINK_OBJ += \
.\.ino.cpp.o \
.\SMDjoystick.cpp.o 

INO_DEPS += \
.\GamePad.ino.d 

CPP_DEPS += \
.\.ino.cpp.d \
.\SMDjoystick.cpp.d 


# Each subdirectory must supply rules for building sources it contributes
.ino.cpp.o: ..\.ino.cpp
	@echo 'Building file: $<'
	@echo 'Starting C++ compile'
	"D:\sloeber\/arduinoPlugin/packages/arduino/tools/avr-gcc/4.9.2-atmel3.5.4-arduino2/bin/avr-g++" -c -g -Os -Wall -Wextra -std=gnu++11 -fpermissive -fno-exceptions -ffunction-sections -fdata-sections -fno-threadsafe-statics -flto -mmcu=atmega328p -DF_CPU=16000000L -DARDUINO=10802 -DARDUINO_AVR_NANO -DARDUINO_ARCH_AVR   -I"D:\sloeber\arduinoPlugin\packages\arduino\hardware\avr\1.6.19\cores\arduino" -I"D:\sloeber\arduinoPlugin\packages\arduino\hardware\avr\1.6.19\variants\eightanaloginputs" -MMD -MP -MF"$(@:%.o=%.d)" -MT"$@" -D__IN_ECLIPSE__=1 -x c++ "$<"  -o  "$@"
	@echo 'Finished building: $<'
	@echo ' '

GamePad.o: ..\GamePad.ino
	@echo 'Building file: $<'
	@echo 'Starting C++ compile'
	"D:\sloeber\/arduinoPlugin/packages/arduino/tools/avr-gcc/4.9.2-atmel3.5.4-arduino2/bin/avr-g++" -c -g -Os -Wall -Wextra -std=gnu++11 -fpermissive -fno-exceptions -ffunction-sections -fdata-sections -fno-threadsafe-statics -flto -mmcu=atmega328p -DF_CPU=16000000L -DARDUINO=10802 -DARDUINO_AVR_NANO -DARDUINO_ARCH_AVR   -I"D:\sloeber\arduinoPlugin\packages\arduino\hardware\avr\1.6.19\cores\arduino" -I"D:\sloeber\arduinoPlugin\packages\arduino\hardware\avr\1.6.19\variants\eightanaloginputs" -MMD -MP -MF"$(@:%.o=%.d)" -MT"$@" -D__IN_ECLIPSE__=1 -x c++ "$<"  -o  "$@"
	@echo 'Finished building: $<'
	@echo ' '

SMDjoystick.cpp.o: ..\SMDjoystick.cpp
	@echo 'Building file: $<'
	@echo 'Starting C++ compile'
	"D:\sloeber\/arduinoPlugin/packages/arduino/tools/avr-gcc/4.9.2-atmel3.5.4-arduino2/bin/avr-g++" -c -g -Os -Wall -Wextra -std=gnu++11 -fpermissive -fno-exceptions -ffunction-sections -fdata-sections -fno-threadsafe-statics -flto -mmcu=atmega328p -DF_CPU=16000000L -DARDUINO=10802 -DARDUINO_AVR_NANO -DARDUINO_ARCH_AVR   -I"D:\sloeber\arduinoPlugin\packages\arduino\hardware\avr\1.6.19\cores\arduino" -I"D:\sloeber\arduinoPlugin\packages\arduino\hardware\avr\1.6.19\variants\eightanaloginputs" -MMD -MP -MF"$(@:%.o=%.d)" -MT"$@" -D__IN_ECLIPSE__=1 -x c++ "$<"  -o  "$@"
	@echo 'Finished building: $<'
	@echo ' '


