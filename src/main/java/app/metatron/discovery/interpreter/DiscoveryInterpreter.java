/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specic language governing permissions and
 * limitations under the License.
 */

package app.metatron.discovery.interpreter;

import org.apache.zeppelin.interpreter.Interpreter;
import org.apache.zeppelin.interpreter.InterpreterContext;
import org.apache.zeppelin.interpreter.InterpreterResult;

import java.util.Properties;

/**
 *
 */
public class DiscoveryInterpreter extends Interpreter {

  public DiscoveryInterpreter(Properties property) {
    super(property);
  }

  public void open() {
  }

  public void close() {
  }

  public InterpreterResult interpret(String s, InterpreterContext interpreterContext) {
    return null;
  }

  public void cancel(InterpreterContext interpreterContext) {
  }

  public FormType getFormType() {
    return null;
  }

  public int getProgress(InterpreterContext interpreterContext) {
    return 0;
  }
}
