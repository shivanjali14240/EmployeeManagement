/*
 * package com.bnt.emplyeemodule.service;
 * 
 * import static org.junit.jupiter.api.Assertions.assertEquals; import static
 * org.junit.jupiter.api.Assertions.assertNotNull; import static
 * org.junit.jupiter.api.Assertions.assertThrows; import static
 * org.junit.jupiter.api.Assertions.assertTrue; import static
 * org.mockito.ArgumentMatchers.any; import static org.mockito.Mockito.when;
 * 
 * import java.util.ArrayList; import java.util.List; import java.util.Optional;
 * 
 * import org.junit.jupiter.api.BeforeEach; import org.junit.jupiter.api.Test;
 * import org.junit.jupiter.api.extension.ExtendWith; import
 * org.mockito.InjectMocks; import org.mockito.Mock; import
 * org.mockito.junit.jupiter.MockitoExtension;
 * 
 * import com.bnt.emplyeemodule.entity.Employee; import
 * com.bnt.emplyeemodule.exception.EmployeeNotFoundException; import
 * com.bnt.emplyeemodule.repository.EmployeeRepo; import
 * com.bnt.emplyeemodule.repository.EmployeeTestAssociationRepository; import
 * com.bnt.emplyeemodule.service.implementation.EmployeeServiceImpl;
 * 
 * @ExtendWith(MockitoExtension.class) public class EmployeeServiceImplTest {
 * 
 * @Mock private EmployeeRepo employeeRepoMock;
 * 
 * @InjectMocks private EmployeeServiceImpl employeeService;
 * 
 * @Mock private EmployeeTestAssociationRepository associationRepository;
 * 
 * private Employee employee1; private Employee employee2;
 * 
 * @BeforeEach public void setUp() { employee1 = new Employee(1L, "shiv",
 * " shinde", "shiv@example.com", "shiva"); employee2 = new Employee(2L,
 * "shivanya", "sawant", "shivanya@example.com", "shivnya"); }
 * 
 * @Test public void testGetAllEmployees() { List<Employee> mockEmployees = new
 * ArrayList<>(); mockEmployees.add(employee1); mockEmployees.add(employee2);
 * when(employeeRepoMock.findAll()).thenReturn(mockEmployees); List<Employee>
 * employees = employeeService.getAllEmployees(); assertEquals(2,
 * employees.size()); assertTrue(employees.contains(employee1));
 * assertTrue(employees.contains(employee2)); }
 * 
 * @Test public void testGetEmployeeById() {
 * when(employeeRepoMock.findById(1L)).thenReturn(Optional.of(employee1));
 * Optional<Employee> result = employeeService.getEmployeeById(1L);
 * assertTrue(result.isPresent()); assertEquals(employee1, result.get()); }
 * 
 * @Test public void testGetEmployeeByIdNotFound() {
 * when(employeeRepoMock.findById(3L)).thenReturn(Optional.empty());
 * employeeService.getEmployeeById(3L); }
 * 
 * @Test public void testCreateEmployee() {
 * when(employeeRepoMock.save(any(Employee.class))).thenReturn(employee1);
 * Employee createdEmployee = employeeService.createEmployee(employee1);
 * assertEquals(employee1, createdEmployee); }
 * 
 * @Test public void testUpdateEmployee() {
 * when(employeeRepoMock.findById(1L)).thenReturn(Optional.of(employee1));
 * when(employeeRepoMock.save(any(Employee.class))).thenReturn(employee2);
 * Employee updatedEmployee = employeeService.updateEmployee(1L, employee2);
 * assertNotNull(updatedEmployee); assertEquals(employee2, updatedEmployee);
 * assertEquals(employee2.getFirstName(), updatedEmployee.getFirstName());
 * assertEquals(employee2.getLastName(), updatedEmployee.getLastName());
 * assertEquals(employee2.getEmail(), updatedEmployee.getEmail()); }
 * 
 * @Test public void testUpdateEmployeeNotFound() {
 * when(employeeRepoMock.findById(3L)).thenReturn(Optional.empty());
 * assertThrows(EmployeeNotFoundException.class, () -> {
 * employeeService.updateEmployee(3L, new Employee()); }); }
 * 
 * }
 */