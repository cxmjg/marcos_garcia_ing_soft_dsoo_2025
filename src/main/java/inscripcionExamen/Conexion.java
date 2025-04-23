package inscripcionExamen;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

public class Conexion {
	private Session session;
	private SessionFactory sessionFactory;
	private Transaction transaction;

	// Constructor
	public Conexion() {
		try {
			Configuration cfg = new Configuration().configure();
			this.sessionFactory = cfg.buildSessionFactory();
			this.session = sessionFactory.openSession();
			this.crearAdmin();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Categoria: Métodos Auxiliares
	// Métodos de utilidad utilizados en la clase

	public static Date stringADate(String string) {
		try {
			DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.FRANCE);
			Date date = formatter.parse(string);
			return date;
		} catch (ParseException e) {
			System.out.println("Exception :" + e);
			return null;
		}
	}

	public Session getSession() {
		return this.session;
	}

	public void setTransaction(Transaction transaction) {
		this.transaction = transaction;
	}

	// Categoria: Usuarios
	// Métodos relacionados con la tabla Usuario

	public Usuario getUsuarioByEmail(String email) {
		try (Session session = sessionFactory.openSession()) {
			Query<Usuario> query = session.createQuery("FROM Usuario WHERE email = :email", Usuario.class)
					.setParameter("email", email);
			return query.uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Boolean getHabilitadoUsuario(String email) {
		try (Session session = sessionFactory.openSession()) {
			Query<Usuario> query = session.createQuery("FROM Usuario WHERE email = :email", Usuario.class)
					.setParameter("email", email);
			 return query.uniqueResult().isHabilitado();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public Usuario getUsuarioById(int id) {
		try (Session session = sessionFactory.openSession()) {
			return session.createQuery("FROM Usuario WHERE id = :id", Usuario.class).setParameter("id", id)
					.uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<Usuario> getUsuarios() {
		try {
			this.transaction = session.beginTransaction();
			Query<Usuario> query = session.createQuery("FROM Usuario WHERE", Usuario.class);
			this.transaction.rollback();
			return query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public void setNombreUsuario(int idUsuario, String nuevoNombre) {
		try (Session session = sessionFactory.openSession()) {
			this.transaction = session.beginTransaction();
			session.createQuery("UPDATE Usuario SET nombre = :nuevoNombre WHERE id = :idUsuario")
					.setParameter("nuevoNombre", nuevoNombre).setParameter("idUsuario", idUsuario).executeUpdate();
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (transaction != null && transaction.getStatus().canRollback()) {
				transaction.rollback();
			}
		}
	}

	public void setApellidoUsuario(int idUsuario, String nuevoApellido) {
		try (Session session = sessionFactory.openSession()) {
			this.transaction = session.beginTransaction();
			session.createQuery("UPDATE Usuario SET apellido = :nuevoApellido WHERE id = :idUsuario")
					.setParameter("nuevoApellido", nuevoApellido).setParameter("idUsuario", idUsuario).executeUpdate();
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (transaction != null && transaction.getStatus().canRollback()) {
				transaction.rollback();
			}
		}
	}

	public void setPasswordUsuario(int idUsuario, String nuevaPassword) {
		try (Session session = sessionFactory.openSession()) {
			this.transaction = session.beginTransaction();
			session.createQuery("UPDATE Usuario SET contrasena = :nuevaPassword WHERE id = :idUsuario")
					.setParameter("nuevaPassword", nuevaPassword).setParameter("idUsuario", idUsuario).executeUpdate();
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (transaction != null && transaction.getStatus().canRollback()) {
				transaction.rollback();
			}
		}
	}

	public void setEmailUsuario(int idUsuario, String nuevoEmail) {
		try (Session session = sessionFactory.openSession()) {
			this.transaction = session.beginTransaction();
			session.createQuery("UPDATE Usuario SET email = :nuevoEmail WHERE id = :idUsuario")
					.setParameter("nuevoEmail", nuevoEmail).setParameter("idUsuario", idUsuario).executeUpdate();
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (transaction != null && transaction.getStatus().canRollback()) {
				transaction.rollback();
			}
		}
	}

	public void setHabilitadoUsuario(int idUsuario, Boolean habilitado) {
		try (Session session = sessionFactory.openSession()) {
			this.transaction = session.beginTransaction();
			session.createQuery("UPDATE Usuario SET habilitado = :habilitado WHERE id = :idUsuario")
					.setParameter("habilitado", habilitado).setParameter("idUsuario", idUsuario).executeUpdate();
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (transaction != null && transaction.getStatus().canRollback()) {
				transaction.rollback();
			}
		}
	}

	public void setRolUsuario(int idUsuario, Rol nuevoRol) {
		try (Session session = sessionFactory.openSession()) {
			this.transaction = session.beginTransaction();
			session.createQuery("UPDATE Usuario SET rol = :nuevoRol WHERE id = :idUsuario")
					.setParameter("nuevoRol", nuevoRol).setParameter("idUsuario", idUsuario).executeUpdate();
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (transaction != null && transaction.getStatus().canRollback()) {
				transaction.rollback();
			}
		}
	}

	public Usuario validarUsuario(String email, String contrasena) {
		try {
			this.transaction = session.beginTransaction();
			Query<Usuario> query = session.createQuery("FROM Usuario WHERE email = :email", Usuario.class);
			query.setParameter("email", email);
			this.transaction.rollback();
			return query.uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public void crearUsuario(String nombre, String apellido, String email, String password, int rol_id,
			Boolean habilitado) {
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		Rol rol = null;
		if (rol_id > 0) {
			rol = getRolById(rol_id);
		}
		Usuario nuevoUsuario = new Usuario(0, password, nombre, apellido, email, rol, habilitado, timestamp);
		try {
			transaction = session.beginTransaction();
			session.save(nuevoUsuario);
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (transaction != null && transaction.getStatus().canRollback()) {
				transaction.rollback();
			}
		}
	}

	public List<Usuario> getDocentes() {
		try {
			this.transaction = session.beginTransaction();
			Query<Usuario> query = session.createQuery("FROM Usuario WHERE rol.id = 2 and habilitado", Usuario.class);
			this.transaction.rollback();
			return query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<Usuario> getAlumnos() {
		try {
			this.transaction = session.beginTransaction();
			Query<Usuario> query = session.createQuery("FROM Usuario WHERE rol.id = 3 and habilitado", Usuario.class);
			this.transaction.rollback();
			return query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// Categoria: Roles
	// Métodos relacionados con la tabla Rol

	public Rol getRolById(int id) {
		try (Session session = sessionFactory.openSession()) {
			return session.createQuery("FROM Rol WHERE id = :id", Rol.class).setParameter("id", id).uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Rol getRolByNombre(String nombre) {
		try (Session session = sessionFactory.openSession()) {
			return session.createQuery("FROM Rol WHERE nombre = :nombre", Rol.class).setParameter("nombre", nombre).uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<Rol> getRoles() {
		try {
			this.transaction = session.beginTransaction();
			Query<Rol> query = session.createQuery("FROM Rol", Rol.class);
			this.transaction.rollback();
			return query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public void crearRol(String nombre, String descripcion) {
		Rol nuevoRol = new Rol(0, nombre, descripcion);
		try {
			transaction = session.beginTransaction();
			session.save(nuevoRol);
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null && transaction.getStatus().canRollback()) {
				transaction.rollback();
			}
			e.printStackTrace();
		}
	}

	// Categoria: Mesas
	// Métodos relacionados con la tabla Mesa

	public Mesa getMesaById(int id) {
		try (Session session = sessionFactory.openSession()) {
			return session.createQuery("FROM Mesa WHERE id = :id", Mesa.class).setParameter("id", id).uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public void crearMesa(String fechaInicio, String fechaFin, String fechaInicioInscripcion,
			String fechaFinInscripcion) {
		Timestamp fechaCreacion = new Timestamp(System.currentTimeMillis());
		Mesa nuevaMesa = new Mesa(0, stringADate(fechaInicio), stringADate(fechaFin),
				stringADate(fechaInicioInscripcion), stringADate(fechaFinInscripcion), fechaCreacion, true);
		try {
			transaction = session.beginTransaction();
			session.save(nuevaMesa);
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null && transaction.getStatus().canRollback()) {
				transaction.rollback();
			}
			e.printStackTrace();
		}
	}

	public void setFechaInicioMesa(int idMesa, String nuevaFecha) {
		try (Session session = sessionFactory.openSession()) {
			Date fechaInicioMesa = new SimpleDateFormat("dd-MM-yyyy").parse(nuevaFecha);
			this.transaction = session.beginTransaction();
			session.createQuery("UPDATE Mesa SET fechaInicio = :nuevaFecha WHERE id = :idMesa")
					.setParameter("nuevaFecha", fechaInicioMesa).setParameter("idMesa", idMesa).executeUpdate();
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (transaction != null && transaction.getStatus().canRollback()) {
				transaction.rollback();
			}
		}
	}

	public void setFechaFinMesa(int idMesa, String nuevaFecha) {
		try (Session session = sessionFactory.openSession()) {
			Date fechaFinMesa = new SimpleDateFormat("dd-MM-yyyy").parse(nuevaFecha);
			this.transaction = session.beginTransaction();
			session.createQuery("UPDATE Mesa SET fechaFin = :nuevaFecha WHERE id = :idMesa")
					.setParameter("nuevaFecha", fechaFinMesa).setParameter("idMesa", idMesa).executeUpdate();
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (transaction != null && transaction.getStatus().canRollback()) {
				transaction.rollback();
			}
		}
	}

	public void setFechaInicioInscripcionMesa(int idMesa, String nuevaFecha) {
		try (Session session = sessionFactory.openSession()) {
			Date fechaInicioInscripcionMesa = new SimpleDateFormat("dd-MM-yyyy").parse(nuevaFecha);
			this.transaction = session.beginTransaction();
			session.createQuery("UPDATE Mesa SET fechaInicioInscripcion = :nuevaFecha WHERE id = :idMesa")
					.setParameter("nuevaFecha", fechaInicioInscripcionMesa).setParameter("idMesa", idMesa)
					.executeUpdate();
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (transaction != null && transaction.getStatus().canRollback()) {
				transaction.rollback();
			}
		}
	}

	public void setFechaFinInscripcionMesa(int idMesa, String nuevaFecha) {
		try (Session session = sessionFactory.openSession()) {
			Date fechaFinInscripcionMesa = new SimpleDateFormat("dd-MM-yyyy").parse(nuevaFecha);
			this.transaction = session.beginTransaction();
			session.createQuery("UPDATE Mesa SET fechaFinInscripcion = :nuevaFecha WHERE id = :idMesa")
					.setParameter("nuevaFecha", fechaFinInscripcionMesa).setParameter("idMesa", idMesa).executeUpdate();
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (transaction != null && transaction.getStatus().canRollback()) {
				transaction.rollback();
			}
		}
	}
	
    public void setHabilitadoMesa(int idMesa, Boolean habilitado) {
        try (Session session = sessionFactory.openSession()) {
            this.transaction = session.beginTransaction();
            session.createQuery("UPDATE Mesa SET habilitado = :habilitado WHERE id = :idMesa")
                    .setParameter("habilitado", habilitado)
                    .setParameter("idMesa", idMesa)
                    .executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null && transaction.getStatus().canRollback()) {
                transaction.rollback();
            }
        }
    }

	public List<Mesa> getMesas() {
		try {
			this.transaction = session.beginTransaction();
			Query<Mesa> query = session.createQuery("FROM Mesa", Mesa.class);
			this.transaction.rollback();
			return query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<Mesa> getMesasHabilitadas() {
		try {
			this.transaction = session.beginTransaction();
			Query<Mesa> query = session.createQuery("FROM Mesa WHERE habilitado", Mesa.class);
			this.transaction.rollback();
			return query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// Categoria: Examenes
	// Métodos relacionados con la tabla Examen

	public Examen getExamenById(int id) {
		try (Session session = sessionFactory.openSession()) {
			Query<Examen> query = session.createQuery(
					"SELECT e FROM Examen e " + "JOIN FETCH e.mesa " + "JOIN FETCH e.docenteTitular "
							+ "JOIN FETCH e.docenteVocal " + "JOIN FETCH e.materia " + "WHERE e.id = :id",
					Examen.class);
			query.setParameter("id", id);
			return query.uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<Examen> getExamenes() {
		try {
			this.transaction = session.beginTransaction();
			Query<Examen> query = session.createQuery("FROM Examen ORDER BY materia.nombre", Examen.class);
			this.transaction.rollback();
			return query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<Examen> getExamenesHabilitados() {
		try {
			this.transaction = session.beginTransaction();
			Query<Examen> query = session.createQuery("FROM Examen WHERE habilitado ORDER BY materia.nombre",
					Examen.class);
			this.transaction.rollback();
			return query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<Examen> getExamenesByDocente(Usuario docente) {
		try {
			this.transaction = session.beginTransaction();
			Query<Examen> query = session
					.createQuery("FROM Examen WHERE (docenteTitular = :docente OR docenteVocal = :docente) AND habilitado")
					.setParameter("docente", docente);
			this.transaction.rollback();
			return query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<Examen> getExamenesByDocentePresidente(Usuario docente) {
		try {
			this.transaction = session.beginTransaction();
			Query<Examen> query = session.createQuery("FROM Examen WHERE docenteTitular = :docente AND habilitado")
					.setParameter("docente", docente);
			this.transaction.rollback();
			return query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<Examen> getExamenesByDocenteVocal(Usuario docente) {
		try {
			this.transaction = session.beginTransaction();
			Query<Examen> query = session.createQuery("FROM Examen WHERE docenteVocal = :docente AND habilitado")
					.setParameter("docente", docente);
			this.transaction.rollback();
			return query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public void crearExamen(String fecha, boolean habilitado, Usuario docenteTitular, Usuario docenteVocal,
			Materia materia, Mesa mesa) {
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		Examen nuevoExamen = new Examen(0, stringADate(fecha), habilitado, timestamp, docenteTitular, docenteVocal,
				materia, mesa);
		try {
			transaction = session.beginTransaction();
			session.save(nuevoExamen);
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null && transaction.getStatus().canRollback()) {
				transaction.rollback();
			}
			e.printStackTrace();
		}
	}

	public void setHabilitadoExamen(int idExamen, Boolean habilitado) {
		try (Session session = sessionFactory.openSession()) {
			this.transaction = session.beginTransaction();
			session.createQuery("UPDATE Examen SET habilitado = :habilitado WHERE id = :idExamen")
					.setParameter("habilitado", habilitado).setParameter("idExamen", idExamen).executeUpdate();
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (transaction != null && transaction.getStatus().canRollback()) {
				transaction.rollback();
			}
		}
	}

	public void setFechaExamen(int idExamen, String nuevaFecha) {
		try (Session session = sessionFactory.openSession()) {
			Date fecha = new SimpleDateFormat("dd-MM-yyyy").parse(nuevaFecha);
			this.transaction = session.beginTransaction();
			session.createQuery("UPDATE Examen SET fecha = :nuevaFecha WHERE id = :idExamen")
					.setParameter("nuevaFecha", fecha).setParameter("idExamen", idExamen).executeUpdate();
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (transaction != null && transaction.getStatus().canRollback()) {
				transaction.rollback();
			}
		}
	}

	public void setMesaExamen(int idExamen, Mesa nuevaMesa) {
		try (Session session = sessionFactory.openSession()) {
			this.transaction = session.beginTransaction();
			session.createQuery("UPDATE Examen SET mesa = :nuevaMesa WHERE id = :idExamen")
					.setParameter("nuevaMesa", nuevaMesa).setParameter("idExamen", idExamen).executeUpdate();
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (transaction != null && transaction.getStatus().canRollback()) {
				transaction.rollback();
			}
		}
	}

	public void setMateriaExamen(int idExamen, Materia nuevaMateria) {
		try (Session session = sessionFactory.openSession()) {
			this.transaction = session.beginTransaction();
			session.createQuery("UPDATE Examen SET materia = :nuevaMateria WHERE id = :idExamen")
					.setParameter("nuevaMateria", nuevaMateria).setParameter("idExamen", idExamen).executeUpdate();
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (transaction != null && transaction.getStatus().canRollback()) {
				transaction.rollback();
			}
		}
	}

	public void setPresidenteExamen(int idExamen, Usuario presidente) {
		try (Session session = sessionFactory.openSession()) {
			this.transaction = session.beginTransaction();
			session.createQuery("UPDATE Examen SET docenteTitular = :presidente WHERE id = :idExamen")
					.setParameter("presidente", presidente).setParameter("idExamen", idExamen).executeUpdate();
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (transaction != null && transaction.getStatus().canRollback()) {
				transaction.rollback();
			}
		}
	}

	public void setVocalExamen(int idExamen, Usuario vocal) {
		try (Session session = sessionFactory.openSession()) {
			this.transaction = session.beginTransaction();
			session.createQuery("UPDATE Examen SET docenteVocal = :vocal WHERE id = :idExamen")
					.setParameter("vocal", vocal).setParameter("idExamen", idExamen).executeUpdate();
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (transaction != null && transaction.getStatus().canRollback()) {
				transaction.rollback();
			}
		}
	}

	// Categoria: Materias
	// Métodos relacionados con la tabla Materia

	public Materia getMateriaById(int id) {
		try (Session session = sessionFactory.openSession()) {
			return session.createQuery("FROM Materia WHERE id = :id", Materia.class).setParameter("id", id)
					.uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<Materia> getMaterias() {
		try {
			this.transaction = session.beginTransaction();
			Query<Materia> query = session.createQuery("FROM Materia ORDER BY nombre", Materia.class);
			this.transaction.rollback();
			return query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<Materia> getMateriasHabilitadas() {
		try {
			this.transaction = session.beginTransaction();
			Query<Materia> query = session.createQuery("FROM Materia WHERE habilitado ORDER BY nombre", Materia.class);
			this.transaction.rollback();
			return query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public void setNombreMateria(int idMateria, String nuevoNombre) {
		try (Session session = sessionFactory.openSession()) {
			this.transaction = session.beginTransaction();
			session.createQuery("UPDATE Materia SET nombre = :nuevoNombre WHERE id = :idMateria")
					.setParameter("nuevoNombre", nuevoNombre).setParameter("idMateria", idMateria).executeUpdate();
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (transaction != null && transaction.getStatus().canRollback()) {
				transaction.rollback();
			}
		}
	}

	public void setHabilitadoMateria(int idMateria, Boolean habilitado) {
		try (Session session = sessionFactory.openSession()) {
			this.transaction = session.beginTransaction();
			session.createQuery("UPDATE Materia SET habilitado = :habilitado WHERE id = :idMateria")
					.setParameter("habilitado", habilitado).setParameter("idMateria", idMateria).executeUpdate();
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (transaction != null && transaction.getStatus().canRollback()) {
				transaction.rollback();
			}
		}
	}

	public void crearMateria(String nombre) {
		Timestamp fechaCreacion = new Timestamp(System.currentTimeMillis());
		Materia nuevaMateria = new Materia(0, nombre, fechaCreacion, true);
		try {
			transaction = session.beginTransaction();
			session.save(nuevaMateria);
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null && transaction.getStatus().canRollback()) {
				transaction.rollback();
			}
			e.printStackTrace();
		}
	}

	// Categoria: Inscripciones a Materias
	// Métodos relacionados con la tabla InscripcionMateria

	public List<InscripcionMateria> getInscripcionesMaterias(int idUsuario) {
		try (Session session = sessionFactory.openSession()) {
			return session.createQuery("FROM InscripcionMateria WHERE alumno_id = :idUsuario", InscripcionMateria.class)
					.setParameter("idUsuario", idUsuario).list();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<InscripcionMateria> getInscripcionesMateriasByAlumno(Usuario alumno) {
		try (Session session = sessionFactory.openSession()) {
			List<InscripcionMateria> inscripciones = session
					.createQuery("FROM InscripcionMateria WHERE alumno = :alumno", InscripcionMateria.class)
					.setParameter("alumno", alumno).list();
			for (InscripcionMateria inscripcion : inscripciones) {
				Hibernate.initialize(inscripcion.getAlumno());
			}
			return inscripciones;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public void crearInscripcionMateria(Usuario alumno, Materia materia, Boolean regular) {
		Timestamp fechaCreacion = new Timestamp(System.currentTimeMillis());
		InscripcionMateria inscripcion = new InscripcionMateria(0, alumno, materia, fechaCreacion, regular);
		try {
			transaction = session.beginTransaction();
			session.save(inscripcion);
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null && transaction.getStatus().canRollback()) {
				transaction.rollback();
			}
			e.printStackTrace();
		}
	}

	public void eliminarInscripcionMateria(Usuario alumno, Materia materia) {
		try (Session session = sessionFactory.openSession()) {
			this.transaction = session.beginTransaction();
			session.createQuery("DELETE InscripcionMateria WHERE alumno = :alumno AND materia = :materia")
					.setParameter("alumno", alumno).setParameter("materia", materia).executeUpdate();
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (transaction != null && transaction.getStatus().canRollback()) {
				transaction.rollback();
			}
		}
	}

	// Categoria: Inscripciones a Exámenes
	// Métodos relacionados con la tabla InscripcionExamen

	public List<InscripcionExamen> getInscripcionesExamenes(int idUsuario) {
		try (Session session = sessionFactory.openSession()) {
			return session.createQuery("FROM InscripcionExamen WHERE alumno_id = :idUsuario", InscripcionExamen.class)
					.setParameter("idUsuario", idUsuario).list();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<InscripcionExamen> getInscripcionesExamenesByAlumno(Usuario alumno) {
		try (Session session = sessionFactory.openSession()) {
			List<InscripcionExamen> inscripciones = session
					.createQuery("FROM InscripcionExamen WHERE alumno = :alumno", InscripcionExamen.class)
					.setParameter("alumno", alumno).list();
			for (InscripcionExamen inscripcion : inscripciones) {
				Hibernate.initialize(inscripcion.getExamen());
				Hibernate.initialize(inscripcion.getExamen().getMateria());
			}
			return inscripciones;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<InscripcionExamen> getInscripcionesExamenesByExamen(Examen examen) {
		try (Session session = sessionFactory.openSession()) {
			List<InscripcionExamen> inscripciones = session
					.createQuery("FROM InscripcionExamen WHERE examen = :examen", InscripcionExamen.class)
					.setParameter("examen", examen).list();
			for (InscripcionExamen inscripcion : inscripciones) {
				Hibernate.initialize(inscripcion.getAlumno());
			}
			return inscripciones;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<InscripcionExamen> getInscripcionesExamenesByAlumnoMateria(Usuario alumno, Materia materia) {
		try (Session session = sessionFactory.openSession()) {
			List<InscripcionExamen> inscripciones = session
					.createQuery("FROM InscripcionExamen WHERE alumno = :alumno AND examen.materia = :materia",
							InscripcionExamen.class)
					.setParameter("alumno", alumno).setParameter("materia", materia).list();
			for (InscripcionExamen inscripcion : inscripciones) {
				Hibernate.initialize(inscripcion.getExamen());
				Hibernate.initialize(inscripcion.getExamen().getMateria());
				Hibernate.initialize(inscripcion.getExamen().getMesa());
			}
			return inscripciones;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public void crearInscripcionExamen(Usuario alumno, Examen examen, Boolean libre) {
		Timestamp fechaCreacion = new Timestamp(System.currentTimeMillis());
		InscripcionExamen inscripcion = new InscripcionExamen(0, fechaCreacion, libre, alumno, examen);
		try {
			transaction = session.beginTransaction();
			session.save(inscripcion);
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null && transaction.getStatus().canRollback()) {
				transaction.rollback();
			}
			e.printStackTrace();
		}
	}

	public void eliminarInscripcionExamen(Usuario alumno, Examen examen) {
		try (Session session = sessionFactory.openSession()) {
			this.transaction = session.beginTransaction();
			session.createQuery("DELETE InscripcionExamen WHERE alumno = :alumno AND examen = :examen")
					.setParameter("alumno", alumno).setParameter("examen", examen).executeUpdate();
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (transaction != null && transaction.getStatus().canRollback()) {
				transaction.rollback();
			}
		}
	}

	// Categoria: Datos de prueba y configuración inicial
	// Métodos utilizados para poblar la base de datos con datos iniciales

	public void crearAdmin() {
		crearRol("Administrador", "Administrador del sistema");
		crearRol("Docente", "Docente");
		crearRol("Alumno", "Alumno");
		crearUsuario("Administrador", "admin", "admin", "1234", 1, true);
		crearUsuario("Docente", "Docente", "docente", "1234", 2, true);
		crearUsuario("Alumno", "Alumno", "alumno", "1234", 3, true);
	}

	public void crearPrueba() {
		// Creación de materias
		crearMateria("Fisica");
		crearMateria("Quimica");
		crearMateria("Historia");
		crearMateria("Geografia");
		crearMateria("Biologia");

		// Creación de docentes
		crearUsuario("Carlos", "Gonzalez", "carlosgonzalez@usuario.com", "1234", 2, true);
		crearUsuario("Maria", "Lopez", "marialopez@usuario.com", "1234", 2, true);
		crearUsuario("Ana", "Martinez", "anamartinez@usuario.com", "1234", 2, true);
		crearUsuario("Luis", "Ramirez", "luisramirez@usuario.com", "1234", 2, true);
		crearUsuario("Sofia", "Fernandez", "sofiafernandez@usuario.com", "1234", 2, true);

		// Creación de alumnos
		crearUsuario("Esteban", "Diaz", "estebandiaz@usuario.com", "1234", 3, true);
		crearUsuario("Laura", "Perez", "lauraperez@usuario.com", "1234", 3, true);
		crearUsuario("Miguel", "Torres", "migueltorres@usuario.com", "1234", 3, true);
		crearUsuario("Patricia", "Mendoza", "patriciamendoza@usuario.com", "1234", 3, true);
		crearUsuario("Javier", "Lopez", "javierlopez@usuario.com", "1234", 3, true);
		crearUsuario("Camila", "Vargas", "camilavargas@usuario.com", "1234", 3, true);
		crearUsuario("Diego", "Silva", "diegosilva@usuario.com", "1234", 3, true);
		crearUsuario("Valeria", "Gomez", "valeriagomez@usuario.com", "1234", 3, true);
		crearUsuario("Ignacio", "Romero", "ignacioromero@usuario.com", "1234", 3, true);
		crearUsuario("Paula", "Reyes", "paulareyes@usuario.com", "1234", 3, true);
		crearUsuario("Pedro", "Suarez", "pedrosuarez@usuario.com", "1234", 3, true);
		crearUsuario("Lucia", "Gomez", "luciagomez@usuario.com", "1234", 3, true);
		crearUsuario("Ricardo", "Alvarez", "ricardoalvarez@usuario.com", "1234", 3, true);
		crearUsuario("Andrea", "Herrera", "andreaherrera@usuario.com", "1234", 3, true);
		crearUsuario("Jorge", "Morales", "jorgemorales@usuario.com", "1234", 3, true);

		// Creación de mesas
		crearMesa("11-10-2024", "11-10-2024", "11-10-2024", "11-10-2024");
		crearMesa("12-10-2024", "12-10-2024", "12-10-2024", "12-10-2024");
		crearMesa("13-10-2024", "13-10-2024", "13-10-2024", "13-10-2024");
		crearMesa("14-10-2024", "14-10-2024", "14-10-2024", "14-10-2024");
		crearMesa("15-10-2024", "15-10-2024", "15-10-2024", "15-10-2024");
		crearMesa("16-10-2024", "16-10-2024", "16-10-2024", "16-10-2024");
		crearMesa("17-10-2024", "17-10-2024", "17-10-2024", "17-10-2024");
		crearMesa("18-10-2024", "18-10-2024", "18-10-2024", "18-10-2024");
		crearMesa("19-10-2024", "19-10-2024", "19-10-2024", "19-10-2024");
		crearMesa("20-10-2024", "20-10-2024", "20-10-2024", "20-10-2024");

		// Obtener materias, mesas y usuarios creados
		List<Materia> materias = getMaterias();
		List<Mesa> mesas = getMesas();
		List<Usuario> docentes = getDocentes();
		List<Usuario> alumnos = getAlumnos();

		// Creación de exámenes adicionales
		for (int i = 1; i <= 20; i++) {
			Usuario docentePresidente = docentes.get((i * 2) % docentes.size());
			Usuario docenteVocal = docentes.get((i * 3) % docentes.size());
			Usuario alumno = alumnos.get((i * 2) % alumnos.size());
			Materia materia = materias.get(i % materias.size());
			Mesa mesa = mesas.get(i % mesas.size());
			crearExamen("10-10-2024", true, docentePresidente, docenteVocal, materia, mesa);
			crearInscripcionMateria(alumno, materia, true);
		}

		List<Examen> examenes = getExamenes();

		for (int i = 1; i <= 50; i++) {
			Usuario alumno = alumnos.get((i * 2) % alumnos.size());
			Examen examen = examenes.get((i * 2) % examenes.size());
			crearInscripcionExamen(alumno, examen, false);
		}
	}

}
