import frida
import sys

js_code = '''
const getFields = (instance) => {
  const classes = instance.getClass();
  return classes.getDeclaredFields();
};

const getFeild = (instance, name) => {
  return getFields(instance).find((f) => {
    return f.getName() === name;
  });
};

const getValue = (instance, field) => {
  const ref = {};
  try {
    field.setAccessible(true);
    ref.value = field.get(instance);
  } catch (e) {
    ref.value = undefined;
  }
  return ref.value;
};

Java.perform(() => {
  const ApiSecurity = Java.use('com.immomo.momoenc.e');
  ApiSecurity.f.implementation = function () {
    this.f();
    const k = getFeild(this, 'd');
    const v = getValue(this, k);
    console.log(v);
  };
});
'''

device = frida.get_usb_device()
session = device.attach('MOMO陌陌')
script = session.create_script(js_code)

def on_message(message):
    print(message)

script.on('message', on_message)
script.load()

sys.stdin.read()
