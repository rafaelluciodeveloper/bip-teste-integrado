import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { ApiService } from './api.service';

describe('ApiService', () => {
  let service: ApiService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [ApiService]
    });
    service = TestBed.inject(ApiService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  describe('GET requests', () => {
    it('should make GET request to correct URL', () => {
      const testData = { id: 1, name: 'Test' };
      const endpoint = '/test';

      service.get(endpoint).subscribe(data => {
        expect(data).toEqual(testData);
      });

      const req = httpMock.expectOne('http://localhost:8080/api/v1/test');
      expect(req.request.method).toBe('GET');
      req.flush(testData);
    });
  });

  describe('POST requests', () => {
    it('should make POST request with data', () => {
      const testData = { name: 'New Item' };
      const endpoint = '/test';
      const responseData = { id: 1, name: 'New Item' };

      service.post(endpoint, testData).subscribe(data => {
        expect(data).toEqual(responseData);
      });

      const req = httpMock.expectOne('http://localhost:8080/api/v1/test');
      expect(req.request.method).toBe('POST');
      expect(req.request.body).toEqual(testData);
      req.flush(responseData);
    });
  });

  describe('PUT requests', () => {
    it('should make PUT request with data', () => {
      const testData = { id: 1, name: 'Updated Item' };
      const endpoint = '/test/1';

      service.put(endpoint, testData).subscribe(data => {
        expect(data).toEqual(testData);
      });

      const req = httpMock.expectOne('http://localhost:8080/api/v1/test/1');
      expect(req.request.method).toBe('PUT');
      expect(req.request.body).toEqual(testData);
      req.flush(testData);
    });
  });

  describe('DELETE requests', () => {
    it('should make DELETE request', () => {
      const endpoint = '/test/1';

      service.delete(endpoint).subscribe();

      const req = httpMock.expectOne('http://localhost:8080/api/v1/test/1');
      expect(req.request.method).toBe('DELETE');
      req.flush({});
    });
  });
});
