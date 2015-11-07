/*
Vcc - 5V
Echo - 13
Trig - 12
Gnd - Gnd
*/

#define echoPin 13 //Pino 13 recebe o pulso do echo
#define trigPin 12 //Pino 12 envia o pulso para gerar o echo

int teste = 0;
int inc = 1;

void setup()
{
   Serial.begin(9600); //inicia a porta serial
   pinMode(echoPin, INPUT); // define o pino 13 como entrada (recebe)
   pinMode(trigPin, OUTPUT); // define o pino 12 como saida (envia)
}

void loop()
{
  teste += inc;
  if(teste >= 90){
    inc = -1;
  }
  if(teste <= 30){
    inc = 1;
  }
  Serial.println(teste);
  delay(100);
}
