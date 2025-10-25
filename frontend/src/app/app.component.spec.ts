import { AppComponent } from './app.component';

describe('AppComponent', () => {
  it('should create the app', () => {
    const app = new AppComponent();
    expect(app).toBeTruthy();
  });

  it(`should have as title 'Sistema de Benefícios'`, () => {
    const app = new AppComponent();
    expect(app.title).toEqual('Sistema de Benefícios');
  });
});
